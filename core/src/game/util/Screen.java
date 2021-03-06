package game.util;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.tann.hexcity.Main;
import com.tann.hexcity.Main.MainState;
import com.tann.hexcity.screens.menu.InputGrabber;

public abstract class Screen extends Group{
	//screenshake stuff//
	private float shakeMagnitude=0;
	private static float shakeFrequency=100;
	private static float shakeDrag=.005f;
	private ArrayList<Particle> particles = new ArrayList<Particle>();
	private ArrayList<Particle> newParticles = new ArrayList<Particle>();
	private ArrayList<Actor> actorStack = new ArrayList<Actor>();
	
	public void pushActor(Actor a){
		Group group = new Group();
		group.addActor(new InputGrabber());
		group.addActor(a);
		actorStack.add(group);
		addActor(group);
	}
	
	public boolean popActor(){
		if(actorStack.size()==0)return false;
		Actor a = actorStack.remove(actorStack.size()-1);
		a.remove();
		return true;
	}
	
	public Screen() {
		setSize(Main.width, Main.height);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		preDraw(batch);
		super.draw(batch, parentAlpha);
		drawParticles(batch);
		postDraw(batch);

	}
	public abstract void preDraw(Batch batch);
	public abstract void postDraw(Batch batch);

	@Override
	public void act(float delta) {
		if(Main.self.getState()==MainState.Paused)return;
		setPosition(getX()+(float)(Math.sin(Main.ticks*shakeFrequency)*shakeMagnitude), 
				getY()+(float) (Math.cos((Main.ticks+100)*shakeFrequency)*shakeMagnitude));
		shakeMagnitude*=Math.pow(shakeDrag, delta);
		tickParticles(delta);
		preTick(delta);
		super.act(delta);
		postTick(delta);
		setPosition((int)getX(), (int)getY());
	}

	public abstract void preTick(float delta);
	public abstract void postTick(float delta);

	public void addParticle(Particle p){
		newParticles.add(p);
	}
	private void tickParticles(float delta) {
		particles.addAll(newParticles);
		newParticles.clear();
		for(int i=particles.size()-1;i>=0;i--){
			Particle p = particles.get(i);
			p.act(delta);
			if(p.dead)particles.remove(p);
		}
	}

	public void drawParticles(Batch batch){
		for(Particle p : particles) p.draw(batch);
	}

	public abstract void keyPressed(int keycode);
}
