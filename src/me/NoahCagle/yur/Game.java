package me.NoahCagle.yur;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import me.NoahCagle.yur.graphics.Game3D;
import me.NoahCagle.yur.graphics.Screen;
import me.NoahCagle.yur.input.InputListener;
import me.NoahCagle.yur.logic.Point;
import me.NoahCagle.yur.world.Boundary;
import me.NoahCagle.yur.world.Ray;
import me.NoahCagle.yur.world.World;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	public static int width = 450;
	public static int height = 350;
	public int scale = 1;

	private boolean running = false;
	private Thread thread;

	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	private Screen screen;

	private Ray[] rays;
	private World world;

	private int camDir = 250;
	private int playerStartX = width / 2;
	private int playerStartY = height / 2;

	private InputListener input;

	private Game3D game3D;

	public Game() {
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);

		screen = new Screen(width, height);

		input = new InputListener();
		addKeyListener(input);
		addMouseMotionListener(input);

		int fov = 60;
		// One ray for each pixel width
		double numRays = width;

		rays = new Ray[(int) numRays];
		double angleInterval = fov / numRays;

		for (int i = 0; i < rays.length; i++) {
			rays[(int) i] = new Ray(playerStartX, playerStartY, i * angleInterval, camDir);
		}

		world = new World();

		create3d();

	}

	public void start() {
		if (!running)
			running = true;
		thread = new Thread(this);
		thread.start();
	}

	public void run() {
		int frames = 0;

		double unprocessedSeconds = 0;
		long lastTime = System.nanoTime();
		double secondsPerTick = 1 / 60.0;
		int tickCount = 0;

		requestFocus();

		while (running) {
			long now = System.nanoTime();
			long passedTime = now - lastTime;
			lastTime = now;
			if (passedTime < 0)
				passedTime = 0;
			if (passedTime > 100000000)
				passedTime = 100000000;

			unprocessedSeconds += passedTime / 1000000000.0;

			boolean ticked = false;
			while (unprocessedSeconds > secondsPerTick) {
				tick();
				unprocessedSeconds -= secondsPerTick;
				ticked = true;

				tickCount++;
				if (tickCount % 60 == 0) {
					System.out.println(frames + " fps");
					lastTime += 1000;
					frames = 0;
				}
			}

			if (ticked) {
				render();
				frames++;
			} else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}

	double playerX = playerStartX;
	double playerY = playerStartY;

	private void tick() {
		input.tick();

		if (input.w || input.up)
			movePlayer(0, 1);
		if (input.a)
			movePlayer(1, 0);
		if (input.s || input.down)
			movePlayer(0, -1);
		if (input.d)
			movePlayer(-1, 0);

		if (input.left)
			camDir--;
		if (input.right)
			camDir++;

		if (camDir > 360)
			camDir = 0;
		if (camDir < 0)
			camDir = 360;

		for (Ray r : rays) {
			r.setOrigin((int) playerX, (int) playerY);
			r.setCamDir(camDir);
		}

		if (game3D != null)
			game3D.tick();

	}

	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		screen.clear();

		for (Ray r : rays) {
			r.draw(screen, (int) playerX, (int) playerY);
			r.detectIntersection(world.getBoundaries());
		}

		for (Boundary b : world.getBoundaries()) {
			b.draw(screen, (int) playerX, (int) playerY);
		}

		screen.sync(pixels);

		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, width * scale, height * scale, null);
		bs.show();
		g.dispose();

		if (game3D != null)
			game3D.render();

	}

	public void stop() {
		if (running)
			running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void create3d() {
		Game3D game = new Game3D(width, height, 2, rays, input);
		JFrame frame = new JFrame();
		frame.add(game);
		frame.pack();
		frame.setTitle("3D");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);

		game3D = game;

	}

	int speed = 2;

	private void movePlayer(int xDir, int yDir) {
		double forwardAngle = Math.toRadians(camDir + 45);
		double strafeAngle = Math.toRadians(camDir - 45);

		double forwardXDir = Math.cos(forwardAngle) * yDir;
		double forwardYDir = Math.sin(forwardAngle) * yDir;

		double strafeXDir = Math.cos(strafeAngle) * xDir;
		double strafeYDir = Math.sin(strafeAngle) * xDir;

		playerX += (strafeXDir + forwardXDir) * speed;
		playerY += (strafeYDir + forwardYDir) * speed;

	}

	public static void main(String[] args) {
		Game game = new Game();
		JFrame frame = new JFrame();
		frame.add(game);
		frame.pack();
		frame.setTitle("Yurr");
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);

		game.start();

	}

}
