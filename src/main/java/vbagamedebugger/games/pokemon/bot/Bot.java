package vbagamedebugger.games.pokemon.bot;

import java.util.Vector;

import vbagamedebugger.gbio.GbButtons;
import vbagamedebugger.gbio.GbIO;
import vbagamedebugger.games.pokemon.model.PokemonGameState;
import vbagamedebugger.games.pokemon.model.State;
import static vbagamedebugger.games.pokemon.model.State.*;
import vbagamedebugger.games.pokemon.model.Pokemon;
import vbagamedebugger.Util;

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

	private final PokemonGameState gs;

	public Bot(PokemonGameState gs) {
		this.gs = gs;
		new Thread(this, "bot").start();
	}

	private void nextMove() {
		System.out.println(this.gs.getState());
		switch (this.gs.getState()) {
		case READING_SIGN:
		case UNKNOWN_TEXT:
			GbIO.press(GbButtons.A);
			break;
		case BATTLE_MAIN:
			if (this.runFromWildPokemon) {
				GbIO.press(GbButtons.DOWN);
				GbIO.press(GbButtons.RIGHT);
			} else {
				GbIO.press(GbButtons.UP);
				GbIO.press(GbButtons.LEFT);
				GbIO.press(GbButtons.A);
			}
			break;
		case CHOOSE_POKEMON:
			GbIO.press(GbButtons.B);
			GbIO.press(GbButtons.LEFT);
			break;
		case MOVE_SELECT:
			int rndMove = Util.rnd.nextInt(4);

			System.out.println("Selectin move: " + rndMove);

			for (int i = 0; i < rndMove; i++) {
				GbIO.press(GbButtons.DOWN);
				this.sleep();
			}

			GbIO.press(GbButtons.A);

			break;
		case WILD_APPEARED:
			GbIO.press(GbButtons.A);
			break;
		case FREEROAM:
			GbIO.press(GbButtons.randomDirection());
			break;
		case ACK_EXPERIENCE:
			GbIO.press(GbButtons.A);

			break;
		default:
			GbIO.press(GbButtons.random());
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
