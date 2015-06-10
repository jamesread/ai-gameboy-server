package vbagamedebugger.games.pokemon;

import java.util.Vector;

import vbagamedebugger.Buttons;
import vbagamedebugger.GbaController;
import vbagamedebugger.Main;

public class Bot implements Runnable {

	private enum Objective {
		EXPLORE;
	}

	private final Vector<Objective> activeObjectives = new Vector<Objective>();

	private final Vector<Pokemon> ownedPokemon = new Vector<Pokemon>();

	private boolean run = true;

	private boolean idle = false;

	private State lastState = State.FREEROAM;
	private final boolean runFromWildPokemon = false;

	private final GameState gs;

	public Bot(GameState gs) {
		this.gs = gs;
		new Thread(this, "bot").start();
	}

	private void nextMove() {
		System.out.println(this.gs.getState());
		switch (this.gs.getState()) {
		case READING_SIGN:
		case UNKNOWN_TEXT:
			GbaController.press(Buttons.A);
			break;
		case BATTLE_MAIN:
			if (this.runFromWildPokemon) {
				GbaController.press(Buttons.DOWN);
				GbaController.press(Buttons.RIGHT);
			} else {
				GbaController.press(Buttons.UP);
				GbaController.press(Buttons.LEFT);
				GbaController.press(Buttons.A);
			}
			break;
		case CHOOSE_POKEMON:
			GbaController.press(Buttons.B);
			GbaController.press(Buttons.LEFT);
			break;
		case MOVE_SELECT:
			int rndMove = Main.rnd.nextInt(4);

			System.out.println("Selectin move: " + rndMove);

			for (int i = 0; i < rndMove; i++) {
				GbaController.press(Buttons.DOWN);
				this.sleep();
			}

			GbaController.press(Buttons.A);

			break;
		case WILD_APPEARED:
			GbaController.press(Buttons.A);
			break;
		case FREEROAM:
			GbaController.press(Buttons.randomDirection());
			break;
		case ACK_EXPERIENCE:
			GbaController.press(Buttons.A);

			break;
		default:
			GbaController.press(Buttons.random());
		}
	}

	@Override
	public void run() {
		this.activeObjectives.add(Objective.EXPLORE);

		while (this.run) {
			try {
				this.sleep();
				this.updateOwnedPokemon();

				if (this.gs.getState() != this.lastState) {
					System.out.println("Environment transition: " + this.gs.getState());
				}

				if (!this.idle) {
					this.nextMove();
				}

				this.lastState = this.gs.getState();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void setIdle(boolean selected) {
		this.idle = selected;
	}

	private void sleep() {
		try {
			Thread.sleep(250);
		} catch (Exception e) {

		}
	}

	public void stop() {
		this.run = false;
	}

	@Override
	public String toString() {
		String ret = "";
		ret += "Tasks: " + this.activeObjectives + "\n";
		ret += "Idle: " + this.idle + "\n";
		ret += "Owned Pokemon: " + this.ownedPokemon + "\n";
		ret += "Run from wild: " + this.runFromWildPokemon + "\n";
		return ret;
	}

	private void updateOwnedPokemon() {
		this.ownedPokemon.clear();
	}
}
