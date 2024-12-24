package com.dimasolo.battleship;

import com.dimasolo.battleship.builder.FleetBuilder;
import com.dimasolo.battleship.builder.ShipBuilder;
import com.dimasolo.battleship.model.ship.Ship;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BattleshipApplicationTests {

	@Test
	void shipBuilderTest() throws Exception {
		ShipBuilder shipBuilder = new ShipBuilder();
		FleetBuilder fleetBuilder = new FleetBuilder(shipBuilder);
		List<Ship> ships = fleetBuilder.buildShipFleet();
		ships.forEach(ship -> System.out.println(ship));
		int[][] gameField = fleetBuilder.getGameField();
		System.out.println(gameField);
		for (int [] a: gameField) {
			System.out.println("\n");
			for (int d: a) {
				System.out.print(d);
			}
		}
	}

}
