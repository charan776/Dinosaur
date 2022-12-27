import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;




public class dinosaur implements ActionListener, MouseListener 
{
	public static dinosaur dino;
	
	public int HEIGHT = 800, WIDTH = 1200;
	
	public render render;
	
	public Rectangle dot;
	
	public ArrayList<Rectangle> columns;
	
	public Random rand;
	
	public int ticks, yMotion;
	public double score;
	public int track;
	
	public boolean gameOver, started = true;
	
	public dinosaur()
	{
		JFrame jframe = new JFrame();
		Timer timer = new Timer(20, this);
		
		render = new render();
		rand = new Random();
		
		jframe.add(render);
		jframe.setTitle("dino");
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setSize(WIDTH,HEIGHT);
		jframe.addMouseListener(this);
		jframe.setVisible(true);
		
		dot = new Rectangle(WIDTH/2 -25,HEIGHT-170,50,50);
		columns = new ArrayList<Rectangle>();

		addColumn(true);
		addColumn(true);
		addColumn(true);
		addColumn(true);

		timer.start();
		
	}
	
	private void addColumn(boolean start) 
	{
		int width = 30+ rand.nextInt(30);
		int height = 30+ rand.nextInt(30);
		
		if(start)
		{
			columns.add(new Rectangle(WIDTH + width + columns.size() * 800,HEIGHT - height-120,width,height));
		}
		else
		{
			columns.add(new Rectangle(columns.get(columns.size()-1).x+600, HEIGHT - height-120,width,height));
		}	
	}
	
	public static void main(String[] args)
	{
		dino = new dinosaur();
	}

	public void repaint(Graphics g) 
	{
		g.setColor(Color.cyan.darker());
		g.fillRect(0, 0, WIDTH, HEIGHT);

		g.setColor(Color.orange.darker().darker());
		g.fillRect(0, HEIGHT - 120, WIDTH, 120);

		g.setColor(Color.green.darker());
		g.fillRect(0, HEIGHT - 120, WIDTH, 20);

		g.setColor(Color.red);
		g.fillRect(dot.x, dot.y, dot.width, dot.height);
		
		for (Rectangle column : columns)
		{
			g.setColor(Color.blue.darker());
			g.fillRect(column.x, column.y, column.width, column.height);
		}
		
		g.setColor(Color.white);
		g.setFont(new Font("Arial", 1, 100));

		if (!started)
		{
			g.drawString("Click to start!", 75, HEIGHT / 2 - 50);
		}

		if (gameOver)
		{
			g.drawString("Game Over!", 100, HEIGHT / 2 - 50);
		}

		if (!gameOver)
		{
			g.drawString(String.valueOf(score), WIDTH / 2 - 25, 100);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		int speed = 10;
		
		
		if (started)
		{
			for (int i = 0; i < columns.size(); i++)
			{
				Rectangle column = columns.get(i);

				column.x -= speed;
			}
			

			for (int i = 0; i < columns.size(); i++)
			{
				Rectangle column = columns.get(i);

				if (column.x + column.width < 0)
				{
					columns.remove(column);
						addColumn(false);
				}
			}
			
			dot.y += yMotion;
			track += yMotion;
			
			if(track == -200)
			{
				yMotion =10;
			}
			
			if(track ==0)
			{
				dot.y = HEIGHT  -120 -dot.height;
				yMotion =0;
			}
			
			
			for (Rectangle column : columns)
			{
				if ( dot.x + dot.width / 2 > column.x + column.width / 2 - 10 && dot.x + dot.width / 2 < column.x + column.width / 2 + 10)
				{
					score = score + .5;
				}

				if (column.intersects(dot))
				{
					gameOver = true;

					if (dot.x <= column.x)
					{
						dot.x = column.x - dot.width;

					}
					else
					{
						if (column.y != 0)
						{
							dot.y = column.y - dot.height;
						}
						else if (dot.y < column.height)
						{
							dot.y = column.height;
						}
					}
				}
			}

			
		}

		render.repaint();
		
	}
	public void jump()
	{

		if (gameOver)
		{
			dot = new Rectangle(WIDTH/2 -25,HEIGHT-170,50,50);
			columns.clear();
			yMotion = 0;
			score = 0;

			addColumn(true);
			addColumn(true);
			addColumn(true);
			addColumn(true);

			gameOver = false;
		}

		if (started != true)
		{
			started = true;
		}
		else if (!gameOver && dot.y== HEIGHT-120 - dot.height)
		{
				yMotion = - 10;
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) 
	{
		jump();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}

