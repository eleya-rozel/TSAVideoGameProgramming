//Text based game
//Created on 10/10/19 by Enya Gu

import java.util.Scanner;
import java.util.Random;

public class Game{
	public static void main(String[] args){
		Scanner scanner = new Scanner(System.in);
        boolean exit = true;
		//outer game loop
		while(exit){
            int location = 0; //initializes location
			int[] visits = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, -1}; //determines how many times you've been in a room, starting at -1 (for the first time you go into that room)
            boolean[] take = {false, false, false, false, false, false, false, false, false, false, false, false}; //turns true if you take a weapon in that room; however, if no weapons are available, it will stay false
            //weapons in order: knife, nothing, practice sword, nothing, butterknife, nothing, nothing, nothing, mystical sword, nothing, gun, cross
			boolean[] loop = {false, false, false, false, false, false, false, false, false, false, false, false}; //turns true if you take a weapon so the take description will not show up again
			boolean[] trainingTime = {false}; //turns true if you choose to train
            boolean[] cookingTime = {false}; //turns true if you choose to cook
            boolean[] shoppingTime = {false}; //turns true if you choose to shop
            boolean[] runningTime = {false}; //turns true if you choose to run
			boolean[] prayingTime = {false}; //turns true if you choose to pray
			//WE DON'T NEED prayingTime, cookingTime, or shoppingTime but if I take them out half of the methods won't work
            int[] timeOfDay = {1}; //phases of the day are determined by this number % 5 in the whatTime method; updates when an action is taken
            //morning, noon, afternoon, evening, night
            int[] playerValues = {30, 50, 10, 0, 0}; //updates when certain actions are done
            //HP, ATK, MP, sandwiches (+HP), potions (+MP)
			int[] localMP = {playerValues[2]}; //for usage in boss fights, when MP needs to be used but doesn't change the actual playerValues for it (aka resets after the end of the fight)
			boolean[] gameEnd = {false}; //when player dies, changes to true

			//inner game loop
			boolean cont = true;
			while(cont){
				take[location] = map(location, visits, take, trainingTime, timeOfDay, cookingTime, shoppingTime, runningTime, prayingTime, loop); //updates take and outputs something
                int previousLocation = location; //for updating visits
				String input = scanner.nextLine(); //input something
				location = parser(input, location, take, timeOfDay, trainingTime, playerValues, cookingTime, shoppingTime, runningTime, prayingTime, gameEnd, localMP); //do something based on the input
				visits[previousLocation] ++; //updates visits
				//checks if the user wants to exit the game
				if(location == -1000){ //exit
					cont = false;
					exit = false;
				}
				if(location == -2000){ //restarts
					cont = false;
				}
			}
		}
	}
	public static int parser(String input, int location, boolean take[], int[] timeOfDay, boolean trainingTime[], int[] playerValues, boolean[] cookingTime, boolean[] shoppingTime, boolean[] runningTime, boolean[] prayingTime, boolean[] gameEnd, int[] localMP){
		//all commands are stored in here
		if(timeOfDay[0] == 5 || timeOfDay[0] == 12 || timeOfDay[0] == 20 || timeOfDay[0] == 26 || timeOfDay[0] == 32 || timeOfDay[0] == 40){
			bossFights(playerValues, take, gameEnd, localMP, timeOfDay);
			if(gameEnd[0] == true){
				return -2000;
			}
		}
		//when the time has changed to a specific value, it'll call the specific fight in the bossFights method
		
		String[] moveList = {"move", "Move", "navigate", "walk", "go", "m"}; //list of valid inputs for move
		boolean moveTrue = false;
		for(String string:moveList){
			if(input.equals(string)){
				moveTrue = true; //return true if it was found
			}
		}
		/* String[] takeList = {"take", "Take", "t"}; //list of valid inputs for take
		boolean takeTrue = false;
		for(String string:takeList){
			if(input.equals(string)){
				takeTrue = true; //return true if it was found
			}
		} */
		/* String[] cookList = {"cook", "Cook", "make food"}; //list of valid inputs for cook
		boolean cookTrue = false;
		for(String string:cookList){
			if(input.equals(string)){
				cookTrue = true; //return true if it was found
			}
		} */
        
        String[] trainList = {"train", "Train", "do something", "practice"}; //list of valid inputs for train
		boolean trainTrue = false;
		for(String string:trainList){
			if(input.equals(string)){
				trainTrue = true; //return true if it was found
			}
        }
        
        /*String[] shoppingList = {"shop", "Shop", "buy", "Buy", "go shopping"}; //list of valid inputs for shop
		boolean shoppingTrue = false;
		for(String string:shoppingList){
			if(input.equals(string)){
				shoppingTrue = true; //return true if it was found
			}
        } */

        String[] runningList = {"run", "Run", "go run", "sprint", "go running"}; //list of valid inputs for run
		boolean runningTrue = false;
		for(String string:runningList){
			if(input.equals(string)){
				runningTrue = true; //return true if it was found
			}
        }

        /* String[] prayList = {"pray", "go pray", "Pray"}; //list of valid inputs for pray
		boolean prayTrue = false;
		for(String string: prayList){
			if(input.equals(string)){
				prayTrue = true;
			}
		} */

		String[] inventoryList = {"inventory", "Inventory", "inv"}; //list of valid inputs for inventory
		boolean inventoryTrue = false;
		for(String string:inventoryList){
			if(input.equals(string)){
				inventoryTrue = true; //return true if it was found
			}
		}

		String[] exitList = {"exit", "Exit", "stop", "quit"}; //list of valid inputs for exit
		boolean exitTrue = false;
		for(String string:exitList){
			if(input.equals(string)){
				exitTrue = true; //return true if it was found
			}
		}
		
		String[] resetList = {"reset", "continue", "start over"}; //list of valid inputs for reset
		boolean resetTrue = false;
		for(String string:resetList){
			if(input.equals(string)){
				resetTrue = true; //return true if it was found
			}
		}
		
		String[] statList = {"stat", "Stat", "st", "stats", "Stats"}; //list of valid inputs for stat
		boolean statTrue = false;
		for(String string:statList){
			if(input.equals(string)){
				statTrue = true; //return true if it was found
			}
		}
		
		Scanner s = new Scanner(System.in);
		if(moveTrue){
			System.out.println("You have selected move.");
			return move(location); //calls this method and returns location
		}
        
		
		/* else if(takeTrue){
            System.out.println("You have selected take.");
			take(location, take); //updates take
			return location;
		} */
		
		/* else if (cookTrue){
			if(location == 4){ //only happens if you're in the right location
				System.out.println("You have selected cook.");
				cooking(cookingTime, timeOfDay, playerValues); //runs cook
				return location;
			}
			else{
				System.out.println("You can't cook here.");
				return location;
			}
        } */
        
        else if(trainTrue){
			if(location == 2){ //only happens if you're in the right location
				System.out.println("You have selected to train.");
				training(timeOfDay, trainingTime, playerValues); //runs training
				return location;
			}
			else{
				System.out.println("You can't train here.");
				return location;
			}
        }

        /*else if(shoppingTrue){
            if(location == 6){ //only happens if you're in the right location
				System.out.println("You have selected to shop.");
				shopping(timeOfDay, shoppingTime, playerValues); //runs shopping
				return location;
			}
			else{
				System.out.println("You can't shop here.");
				return location;
			}
        } */

        else if(runningTrue){
            if(location == 7){ //only happens if you're in the right location
				System.out.println("You have selected to run.");
				running(timeOfDay, runningTime, playerValues); //runs running
				return location;
			}
			else{
				System.out.println("You can't run here.");
				return location;
			}
        }
		
		/* else if(prayTrue){
			if(location == 8){ //only happens if you're in the right location
				System.out.println("You have selected to pray.");
				praying(timeOfDay, prayingTime, playerValues); //runs praying
				return location;
			}
			else{
				System.out.println("You can't pray here.");
				return location;
			}
        } */
		
		else if(statTrue){ //displays current player stats
            System.out.println("Your stats are as follows.");
			System.out.println("HP: " + playerValues[0]);
			System.out.println("ATK: " + playerValues[1]);
			System.out.println("MP: " + playerValues[2]);
			//System.out.println("Sandwiches (+HP): " + playerValues[3]);
			//System.out.println("Potions (+MP): " + playerValues[4]);
			return location;
		}

		/* else if(inventoryTrue){ //displays current player inventory
			System.out.println("Your inventory is as follows.");
			if(take[0] == true){
				System.out.println("Knife - 75% accuracy, 5ATK");
			}
			if (take[2] == true){
				System.out.println("Practice sword - 100% accuracy, 3ATK");
			}
			if (take[4] == true){
				System.out.println("Butterknife - 100% accuracy, 1ATK, 15ATK when crit (5% chance)");
			}
			if (take[8] == true){
				System.out.println("Mystical sword - 20% accuracy, 10ATK");
			}
			if (take[10] == true){
				System.out.println("Gun - 50% accuracy, 8ATK");
			}
			if (take[11] == true){
				System.out.println("Cross - 100% accuracy, 1ATk");
			}
			return location;
		} */

		else if (exitTrue){
			System.out.println("Do you really want to quit?");
			if (s.nextLine().equals("Y")){
				return -1000; //return -1000 to act as a code to exit program
			}
		}
		else if (resetTrue){
			System.out.println("Do you want to reset?");
			if (s.nextLine().equals("Y")){
				return -2000; //return -2000 to act as a code to restart program
			}
		}
		
		else{
			System.out.println("I don't understand");
		}
		return location;
	}
    

	public static int moveDir(){ //move access this to determine how to move
		Scanner s = new Scanner(System.in);
		System.out.println("Please enter a direction to move");
		String input = s.nextLine();
		
		String[] northList = {"north", "North", "n"}; //direction
		for(String string:northList){
			if(input.equals(string)){
				return 0;
			}
		}
		String[] southList = {"south", "South", "s"}; //direction
		for(String string:southList){
			if(input.equals(string)){
				return 1;
			}
		}
		String[] eastList = {"east", "East", "e"}; //direction
		for(String string:eastList){
			if(input.equals(string)){
				return 2;
			}
		}
		String[] westList = {"West", "west", "w"}; //direction
		for(String string:westList){
			if(input.equals(string)){
				return 3;
			}
        }
        String[] northEastList = {"Northeast", "northeast", "ne"}; //direction
        for(String string:northEastList){
            if(input.equals(string)){
                return 4;
            }
        }
        String[] southEastList = {"Southeast", "southeast", "se"}; //direction
        for(String string:southEastList){
            if(input.equals(string)){
                return 5;
            }
        }
        String[] southWestList = {"Southwest", "southwest", "sw"}; //direction
        for(String string:southWestList){
            if(input.equals(string)){
                return 6;
            }
        }
        String[] northWestList = {"Northwest", "northwest", "nw"}; //direction
        for(String string:northWestList){
            if(input.equals(string)){
                return 7;
            }
        }
        return 8;
	}
	
	public static int move(int location){
		int dir = moveDir(); //accesses movedir to know how to move
		
		if (dir == 8){ //if input is not valid
			System.out.println("That's not a direction.");
			return location;
		}
		
		//where the player would go after chosing directions
		//				  n, s, e, w, ne, se, sw, nw
        int[] yourRoom = {0, 1, 0, 0, 0, 0, 0, 0};
        int[] houseHallway = {0, 2, 3, 4, 1, 1, 1, 1};
        int[] houseDojo = {1, 2, 2, 2, 2, 2, 2, 2};
        int[] houseStairs = {3, 3, 3, 1, 3, 3, 3, 3};
        int[] houseKitchen = {4, 4, 1, 5, 4, 4, 4, 4};
        int[] houseDoor = {5, 5, 4, 9, 5, 5, 5, 5};
        int[] outsideLocation = {9, 6, 5, 8, 9, 10, 7, 9};
        int[] shoppingDistrict = {9, 6, 10, 7, 6, 6, 6, 8};
        int[] schoolLocation = {8, 7, 6, 7, 9, 7, 7, 7};
        int[] templeLocation = {8, 7, 9, 8, 8, 6, 8, 8};
        int[] bridgeLocation = {10, 10, 11, 6, 10, 10, 10, 10};
        int[] churchLocation = {11, 11, 11, 10, 11, 11, 11, 11};

		if(location == 0){
			return yourRoom[dir];
		}
		else if(location == 1){
			return houseHallway[dir];
		}
		else if(location == 2){
			return houseDojo[dir];
		}
		else if(location == 3){
			return houseStairs[dir];
		}
		else if(location == 4){
			return houseKitchen[dir];
		}
		else if(location == 5){
			return houseDoor[dir];
		}
		else if(location == 6){
			return shoppingDistrict[dir];
		}
		else if(location == 7){
			return schoolLocation[dir];
		}
		else if(location == 8){
			return templeLocation[dir];
        }
        else if(location == 9){
            return outsideLocation[dir];
        }
        else if(location == 10){
            return bridgeLocation[dir];
        }
        else if(location == 11){
            return churchLocation[dir];
        }
		return location;
	}
    
    public static void take(int location, boolean take[]){ //updates take array correspondingly
		if(location == 0) take[0] = true;
		else if (location == 1) take[1] = true;
		else if (location == 2) take[2] = true;
		else if (location == 3) take[3] = true;
		else if (location == 4) take[4] = true;
		else if (location == 5) take[5] = true;
		else if (location == 6) take[6] = true;
		else if (location == 7) take[7] = true;
		else if (location == 8) take[8] = true;
		else if (location == 9) take[9] = true;
		else if (location == 10) take[10] = true;
		else if (location == 11) take[11] = true;
		else System.out.println("How did you get here?");
	}

    public static int training(int[] timeOfDay, boolean[] trainingTime, int[] playerValues){
        trainingTime[0] = true; //sets value to true
        playerValues[1] += 10; //adds ATK to player stat
        timeOfDay[0] ++; //moves the time forward
        return timeOfDay[0]; 
    }
    /* public static int cooking(boolean[] cookingTime, int[] timeOfDay, int[] playerValues){
        cookingTime[0] = true; //sets value to true
        playerValues[3] += 1; //adds a sandwich to player stat
        timeOfDay[0] ++; //moves the time forward
        return timeOfDay[0];
    }

    public static int shopping(int[] timeOfDay, boolean[] shoppingTime, int[] playerValues){
        shoppingTime[0] = true; //sets value to true
        playerValues[4] += 1; //adds a potion to player stat
        timeOfDay[0] ++; //moves the time forward
        return timeOfDay[0];
    } */

    public static int running(int[] timeOfDay, boolean[] runningTime, int[] playerValues){
        runningTime[0] = true; //sets value to true
        playerValues[0] += 10; //adds HP to player stat
        timeOfDay[0] ++; //moves the time forward
        return timeOfDay[0];
    }
    
	/* public static int praying(int[] timeOfDay, boolean[] prayingTime, int[] playerValues){
		prayingTime[0] = true; //sets value to true
        playerValues[2] += 10; //adds MP to player stat
        timeOfDay[0] ++; //moves the time forward
        return timeOfDay[0];
	} */
	
	public static boolean map(int location, int[] visits, boolean[] take, boolean[] trainingTime, int[] timeOfDay, boolean[] cookingTime, boolean[] shoppingTime, boolean[] runningTime, boolean[] prayingTime, boolean[] loop){
		//calls a room based on your location
		if(location == 0) return yourRoom (visits[location], take[location], timeOfDay[0], loop);
		else if (location == 1) return houseHallway(visits[location], take[location], timeOfDay[0]);
        else if (location == 2) return houseDojo(visits[location], take[location], trainingTime, timeOfDay[0], loop);
		else if (location == 3) return houseStairs(visits[location], take[location], timeOfDay[0]);
		else if (location == 4) return houseKitchen(visits[location], take[location], timeOfDay[0], cookingTime, loop);
		else if (location == 5) return houseDoor(visits[location], take[location], timeOfDay[0]);
		else if (location == 6) return shoppingDistrict(visits[location], take[location], timeOfDay[0], shoppingTime);
		else if (location == 7) return schoolLocation(visits[location], take[location], timeOfDay[0], runningTime);
        else if (location == 8) return templeLocation(visits[location], take[location], timeOfDay[0], prayingTime, loop);
        else if (location == 9) return outsideLocation(visits[location], take[location], timeOfDay[0]);
        else if (location == 10) return bridgeLocation(visits[location], take[location], timeOfDay[0], loop);
        else if (location == 11) return churchLocation(visits[location], take[location], timeOfDay[0], loop);
		return false;
    }
    
    public static String whatTime(int timeOfDay){ //returns a string based on the time it is
        if(timeOfDay % 5 == 1){
            String morning = "It is the morning.";
            return morning;
        }
        else if(timeOfDay % 5 == 2){
            String noon = "It is noon.";
            return noon;
        }
        else if(timeOfDay % 5 == 3){
            String afternoon = "It is the afternoon.";
            return afternoon;
        }
        else if(timeOfDay % 5 == 4){
            String evening = "It is the evening.";
            return evening;
        }
        else if(timeOfDay % 5 == 0){
            String nighttime = "It is nighttime.";
            return nighttime;
        }
        else{
            return "How did you get here?";
        }
    }

	public static boolean yourRoom(int visits, boolean take, int timeOfDay, boolean[] loop){	
		if (visits == -1){ //first time in the room it will display the following
            /* System.out.println("Welcome to the Sixth Holy Grail War.");
            System.out.println("There are 7 participants, aka Masters. Each has summoned a hero from the past to help them fight each other!");
            System.out.println("You have summoned KING ARTHUR to aid you in your fight. He is of the SABER class (he uses a sword, shocking)!");
            System.out.println("The other classes are Archer, Lancer, Rider, Assassin, Caster, and Berserker. You will encounter each of these on your journey to win the war.");
            System.out.println("Your prize is the Holy Grail, a mystical item that will grant you anything you want.");
            System.out.println("It's time for you to begin preparing! You can spend your time in various locations. Try taking things along the way (take).");
			System.out.println("To display your current stats, type in 'stats'. To display inventory, type in 'inventory'.");
			*/
		}
		System.out.println("Location: Your room."); //always displays location and time
        System.out.println(whatTime(timeOfDay));
		if (take == true){
			/* if (loop[0] == false){ //when you pick up the knife the following will display once
				System.out.println("You pick up the knife.");
				loop[0] = true;
			} */
			return true;
		}
		else{
			return false;
		}
    }

	public static boolean houseHallway(int visits, boolean take, int timeOfDay){
        /* if (take == true){ //if player tries to take
            System.out.println("There's nothing here.");
			take = false;
        } */
		System.out.println("Location: The hallway."); //always displays location and time
        System.out.println(whatTime(timeOfDay));
		return false;
	}
	public static boolean houseDojo(int visits, boolean take, boolean[] trainingTime, int timeOfDay, boolean[] loop){
		if (visits == -1){ //first time in the room it will display the following
            System.out.println("You can choose to train in the dojo. It might help your fighting abilities.");
        }
        if (trainingTime[0] == true){ //if player selected to train following displays
            System.out.println("You have decided to train. Your ATK has gone up by 10.");
            trainingTime[0] = false;
        }
        System.out.println("Location: The dojo.");
		System.out.println(whatTime(timeOfDay));
		/* if (take == true){
			if(loop[2] == false){ //when you pick up the prcatice sword the following will display once
				System.out.println("There are practice swords in the dojo. You take one.");
				loop[2] = true;
			}
			return true;
		}
		else{
			return false;
		} */
		return false;
	}
	public static boolean houseStairs(int visits, boolean take, int timeOfDay){
        System.out.println("The stairwell.");
        if (visits == -1){ //first time in the room it will display the following
            System.out.println("The attic is blocked off.");
        }
		/* if (take == true){ //if player tries to take
            System.out.println("There's nothing here.");
			take = false;
        } */
        System.out.println(whatTime(timeOfDay)); //always displays location and time
		return false;
	}
	public static boolean houseKitchen(int visits, boolean take, int timeOfDay, boolean[] cookingTime, boolean[] loop){
		if (visits == -1){  //first time in the room it will display the following
            System.out.println("It's the kitchen.");
        }
        /* if (cookingTime[0] == true){ //when player selects cook
            System.out.println("You cooked up a nice sandwich. It might restore some health in a fight.");
            cookingTime[0] = false;
        } */
        System.out.println("Location: The kitchen."); //always displays location and time
		System.out.println(whatTime(timeOfDay));
		/*if (take == true){ //when you pick up the butterknife the following will display once
			if(loop[4] == false){ 
				System.out.println("You take a trusty butterknife. What could be better in a fight? Nothing!");
				loop[4] = true;
			}
			return true;
		}
		else{
			return false;
		} */
		return false;
	}
	public static boolean houseDoor(int visits, boolean take, int timeOfDay){
        /* if (take == true){ //if player tries to take
            System.out.println("There's nothing here.");
			take = false;
        } */
		System.out.println("Location: The front door."); //always displays location and time
        System.out.println(whatTime(timeOfDay));
		return false;
	}
	public static boolean shoppingDistrict(int visits, boolean take, int timeOfDay, boolean[] shoppingTime){
        if (visits == -1){ //first time in the room it will display the following
            System.out.println("You can spend your time shopping.");
        }
        /* if (shoppingTime[0] == true){ //when player chooses to shop the following is displayed
            System.out.println("You shop around for restoration items, and you got a potion. It might come in handy when doing magic.");
            shoppingTime[0] = false;
		}
		if (take == true){
            System.out.println("There's nothing here."); //if player tries to take
			take = false;
        } */
        System.out.println("Location: The shopping district."); //always displays location and time
        System.out.println(whatTime(timeOfDay));
		return false;
	}
	public static boolean schoolLocation(int visits, boolean take, int timeOfDay, boolean[] runningTime){
        if (visits == -1){ //first time in the room it will display the following
            System.out.println("There's a track here. You can spend time running to up your health.");
        }
        if (runningTime[0] == true){ //when player chooses to run the following is displayed
            System.out.println("You run around the track a bunch. Really worked up a sweat! You feel better. Your HP increased by 10.");
            runningTime[0] = false;
        }
		/* if (take == true){ //if player tries to take
            System.out.println("There's nothing here.");
			take = false;
        } */
        System.out.println("Location: The school."); //always displays location and time
        System.out.println(whatTime(timeOfDay));
		return false;
	}
	public static boolean templeLocation(int visits, boolean take, int timeOfDay, boolean[] prayingTime, boolean[] loop){
		if (visits == -1){ //first time in the room it will display the following
            //System.out.println("There's a mystical aura around this place. You can spend your time praying at the altar to perhaps increase your magical abilities.");
        }
        if (prayingTime[0] == true){ //when player chooses to pray the following is displayed
			//System.out.println("You pray for a while. It's a bit awkward. However, you feel your MP increase by 10.");
			prayingTime[0] = false;
		}
        System.out.println("Location: The temple."); //always displays location and time
		System.out.println(whatTime(timeOfDay));
		/*if (take == true){
            if(loop[8] == false){ //when you pick up the mystical sword the following will display once
				System.out.println("You find a mystical sword in the back of the temple. It's very pointy.");
				loop[8] = true;
			}
			return true;
        }
		else{
			return false;
		} */
		return false;
    }
    public static boolean outsideLocation(int visits, boolean take, int timeOfDay){
		/* if (take == true){ //if player tries to take
            System.out.println("There's nothing here.");
			take = false;
        } */
        System.out.println("Location: Outside the house."); //always displays location and time
        System.out.println(whatTime(timeOfDay));
		return false;
    }
    public static boolean bridgeLocation(int visits, boolean take, int timeOfDay, boolean[] loop){
		System.out.println("Location: The bridge."); //always displays location and time
        System.out.println(whatTime(timeOfDay));
		/* if (take == true){
			if(loop[10] == false){ //when you pick up the gun the following will display once
				System.out.println("You find a gun in the bushes. Just how dangerous is your neighbourhood?");
				loop[10] = true;
			}
			return true;
        }
		else{
			return false;
		} */
		return false;
    }
    public static boolean churchLocation(int visits, boolean take, int timeOfDay, boolean[] loop){
		System.out.println("Location: The church."); //always displays location and time
		System.out.println(whatTime(timeOfDay));
		/* if (take == true){
			if(loop[11] == false){ //when you pick up the cross the following will display once
				System.out.println("You find a cross. It seems pretty useless.");
				loop[11] = true;
			}
			return true;
		}
		else{
			return false;
		}*/
		return false;
    }
	/* public static int physicalAttacking(boolean[] take, int[] playerValues){ //called to return a number for the physical attack
		Random random = new Random();
		String[] knifeList = {"knife", "Knife", "k"};
		String[] practiceSwordList = {"Practice sword", "practice sword", "ps"};
		String[] butterknifeList = {"Butterknife", "butterknife", "bk"};
		String[] mysticalSwordList = {"Mystical sword", "mystical sword", "ms"};
		String[] gunList = {"gun", "Gun", "g"};
		String[] crossList = {"Cross", "cross", "c"};
		System.out.println("All weapon ATKs are multiplied by your base ATK to get the final damage that is done to the boss.");
		System.out.println("The following are available weapons: "); //displays items the player has taken
		if(take[0] == true){
			System.out.println("Knife - 75% accuracy, 5ATK");
		}
		if (take[2] == true){
			System.out.println("Practice sword - 100% accuracy, 3ATK");
		}
		if (take[4] == true){
			System.out.println("Butterknife - 100% accuracy, 1ATK, 15ATK when crit (5% chance)");
		}
		if (take[8] == true){
			System.out.println("Mystical sword - 20% accuracy, 10ATK");
		}
		if (take[10] == true){
			System.out.println("Gun - 50% accuracy, 8ATK");
		}
		if (take[11] == true){
			System.out.println("Cross - 100% accuracy, 1ATk");
		}
		
		int loop = 1;
		while(loop == 1){
			Scanner s = new Scanner(System.in);
			System.out.println("Select a weapon to attack with.");
			String input = s.nextLine();
			//user chooses the item they want to use to attack with
			for(String string: knifeList){
				if(input.equals(string)){
					if(take[0] == true){
						System.out.println("You have chosen the knife to attack with.");
						int atk = random.nextInt(4); //random number from 0-3
						if(atk >= 1){
							return 5;
						}
						else{ //if not >= 2 then the attack misses
							System.out.println("You miss!");
							return 0;
						}
					}
					else{
						System.out.println("You do not have this weapon.");
					}
				}
			}

			for(String string: practiceSwordList){
				if(input.equals(string)){
					if(take[2] == true){
						System.out.println("You have chosen the practice sword to attack with.");
						return 3; //this item won't miss
					}
					else{
						System.out.println("You do not have this weapon.");
					}
				}
			}

			for(String string: butterknifeList){
				if(input.equals(string)){
					if(take[4] == true){
						System.out.println("You have chosen the butterknife to attack with.");
						int atk = random.nextInt(100); //random number from 0-99
						if(atk >= 5){
							return 1;
						}
						else{ //has a 5% chance of crit
							System.out.println("Critical hit!");
							return 15;
						}
					}
					else{
						System.out.println("You do not have this weapon.");
					}
				}
			}

			for(String string: mysticalSwordList){
				if(input.equals(string)){
					if(take[8] == true){
						System.out.println("You have chosen the mystical sword to attack with.");
						int atk = random.nextInt(100); //random number from 0-99
						if(atk >= 20){ //80% chance of missing
							System.out.println("You miss!");
							return 0;
						}
						else{
							return 10;
						}
					}
					else{
						System.out.println("You do not have this weapon.");
					}
				}
			}

			for(String string: gunList){
				if(input.equals(string)){
					if(take[10] == true){
						System.out.println("You have chosen the gun to attack with.");
						int atk = random.nextInt(1); //random number from 0-99
						if(atk == 1){ //50% chance of hit
							return 8;
						}
						else{
							System.out.println("You miss!");
							return 0;
						}
					}
					else{
						System.out.println("You do not have this weapon.");
					}
				}
			}

			for(String string: crossList){
				if(input.equals(string)){
					if(take[11] == true){
						System.out.println("You have chosen the cross to attack with.");
						return 1; //always returns this
					}
					else{
						System.out.println("You do not have this weapon.");
					}
				}
			}
			System.out.println("You have nothing to attack with!");
			return 0;
		}
		return 0;
	} */
	public static int riderATK(){
		//chooses Rider's attack from 4 different options
		Random random = new Random();
		int atk = random.nextInt(4); //random number 0-3

		if(atk == 0){
			//System.out.println("Rider uses: Mystic Eyes of Petrification. -3 HP to player");
			return 3;
		}
		else if(atk == 1){
			//System.out.println("Rider uses: Blood Fort Andromeda. -5 HP to player");
			return 5;
		}
		else if(atk == 2){
			//System.out.println("Rider uses: Pandemonium Cetus. -7 HP to player");
			return 7;
		}
		else if(atk == 3){
			//System.out.println("Rider uses: Bellerophon's Charge. -10 HP to player");
			return 10;
		}
		else{
			return 0;
		}
	}
	/* public static int magicAttacking(int[] playerValues, int[] localMP){ //selects spell to use
		Random random = new Random();
		System.out.println("The following are available spells: ");
		System.out.println("Excalibur: -50MP, does 1000 ATK");
		System.out.println("Gandr: -10MP, does 50 ATK");
		System.out.println("Unlimited Blade Works: -20MP, does 100ATK");
		System.out.println("You have " + localMP[0] + " MP");
		String[] excaliburList = {"Excalibur", "excalibur", "e"};
		String[] gandrList = {"gandr", "Gandr", "g"};
		String[] ubwList = {"Unlimited Blade Works", "unlimited blade works", "ubw"};

		int loop = 1;
		while(loop == 1){
			Scanner s = new Scanner(System.in);
			System.out.println("Select a spell to attack with.");
			String input = s.nextLine();
			if(localMP[0] < 5){
				//need at least 5 MP to use a spell
				System.out.println("Uh oh, you don't have enough MP to do anything!");
				return 0;
			}
			for(String string: excaliburList){
				if(input.equals(string)){
					if(localMP[0] < 30){ //need 30 MP to use spell
						System.out.println("You do not have enough MP to use this spell.");
					}
					else{
						System.out.println("You have chosen to use Excalibur.");
						localMP[0] -= 30; //deletes 30 MP from the user's MP in the fight
						return 1000;
					}
				}
			}
			for(String string: gandrList){
				if(input.equals(string)){
					if(localMP[0] < 5){ //need 5 MP to use spell
						System.out.println("You do not have enough MP to use this spell.");
					}
					else{
						System.out.println("You have chosen to use Gandr. It deals 50 damage.");
						localMP[0] -= 5; //deletes 5 MP from the user's MP in the fight
						return 50;
					}
				}
			}
			for(String string: ubwList){
				if(input.equals(string)){
					if(localMP[0] < 10){ //need 10 MP to use spell
						System.out.println("You do not have enough MP to use this spell.");
					}
					else{
						System.out.println("You have chosen to use Unlimited Blade Works. It deals 100 damage.");
						localMP[0] -= 10; //deletes 10 MP from the user's MP in the fight
						return 100;
					}
				}
			}
		}
		return 0; 
	}*/
	/* public static int usingSandwich(int[] playerValues){
		if(playerValues[3] <= 0){ //if no sandwiches
			System.out.println("You don't have any sandwiches!");
			return 0;
		}
		else{ //raises HP and subtracts a sandwich from the overall inventory
			System.out.println("You have chosen to use a sandwich. Your health has been raised by 10HP.");
			playerValues[3] --;
			return 10;
		}
	}
	public static int usingPotion(int[] playerValues){
		if(playerValues[4] <= 0){ //if no potions
			System.out.println("You don't have any potions!");
			return 0;
		}
		else{ //raises MP and subtracts a potion from the overall inventory
			System.out.println("You have chosen to use a potion. Your MP has been raised by 10MP.");
			playerValues[4] --;
			return 10;
		}
	}
	public static int berserkerATK(){
		//chooses Berserker's attack from 4 different options
		Random random = new Random();
		int atk = random.nextInt(4); //random number 0-4

		if(atk == 0){
			System.out.println("Berserker uses: Nameless Axe-Sword. -5 HP to player");
			return 5;
		}
		else if(atk == 1){
			System.out.println("Berserker uses: Eye of the Mind. -8 HP to player");
			return 8;
		}
		else if(atk == 2){
			System.out.println("Berserker uses: Battle Continuation. -10 HP to player");
			return 10;
		}
		else if(atk == 3){
			System.out.println("Berserker uses: God Hand. -15 HP to player");
			return 15;
		}
		else{
			return 0;
		}
	}
	public static int casterATK(){
		//chooses Caster's attack from 4 different options
		Random random = new Random();
		int atk = random.nextInt(4); //random number 0-4

		if(atk == 0){
			System.out.println("Caster uses: Atlas. -5 HP to player");
			return 5;
		}
		else if(atk == 1){
			System.out.println("Caster uses: Aero. -8 HP to player");
			return 8;
		}
		else if(atk == 2){
			System.out.println("Caster uses: Celaeno. -10 HP to player");
			return 10;
		}
		else if(atk == 3){
			System.out.println("Caster uses: Machia Hecatia Graea. -15 HP to player");
			return 15;
		}
		else{
			return 0;
		}
	}
	public static int lancerATK(){
		//chooses Lancer's attack from 4 different options
		Random random = new Random();
		int atk = random.nextInt(4); //random number 0-4
		if(atk == 0){
			System.out.println("Lancer uses: Spear Assault. -10 HP to player");
			return 10;
		}
		else if(atk == 1){
			System.out.println("Lancer uses: Gae Bolg. -14 HP to player");
			return 14;
		}
		else if(atk == 2){
			System.out.println("Lancer uses: Rune Spear. -17 HP to player");
			return 17;
		}
		else if(atk == 3){
			System.out.println("Lancer uses: Blessing of Wakefulness. -23 HP to player");
			return 23;
		}
		else{
			return 0;
		}
	}
	public static int assassinATK(){
		//chooses Assassin's attack from 4 different options
		Random random = new Random();
		int atk = random.nextInt(4); //random number 0-4
		if(atk == 0){
			System.out.println("Assassin uses: Monohoshi Zao. -20 HP to player");
			return 20;
		}
		else if(atk == 1){
			System.out.println("Assassin uses: Knowldge of Respect and Harmony. -30 HP to player");
			return 30;
		}
		else if(atk == 2){
			System.out.println("Assassin uses: Vitrification. -40 HP to player");
			return 40;
		}
		else if(atk == 3){
			System.out.println("Assassin ues: Tsubame Gaeshi. -50 HP to player");
			return 50;
		}
		else{
			return 0;
		}
	}
	public static int archerATK(){
		//chooses Archer's attack from 4 different options
		Random random = new Random();
		int atk = random.nextInt(4); //random number 0-4
		if(atk == 0){
			System.out.println("Archer uses: Gate of Babylon. -30 HP to player");
			return 30;
		}
		else if(atk == 1){
			System.out.println("Archer uses: Golden Armor. -20 HP to player");
			return 20;
		}
		else if(atk == 2){
			System.out.println("Archer uses: Enkidu - The Chains of Heavenh. -40 HP to player");
			return 40;
		}
		else if(atk == 3){
			System.out.println("Archer uses: Ea - The Sword of Rupture. -50 HP to player");
			return 50;
		}
		else{
			return 0;
		}
	} */
	public static int bossFights(int[] playerValues, boolean[] take, boolean[] gameEnd, int[] localMP, int[] timeOfDay){ //method that is called for all boss fights
		int bossHP = 1; //initializes variable
		int playerHP = playerValues[0]; //local player HP to not affect the array itself
		Scanner s = new Scanner(System.in);
		int loop = 1;
		//String[] potionList = {"Potion", "potion", "p"};
		//String[] sandwichList = {"sandwich", "Sandwich", "s"};
		int start = 1; //initializes variable
		String bossName = "Hello";
		
		while(loop == 1){
			if(start == 1){ //only displays this at the start of the fight
				if(timeOfDay[0] == 5){ //first boss fight
					System.out.println("An enemy Servant has appeared! They are Medusa, of the Rider class, with Pegasus.");
					bossName = "Rider";
					bossHP = 500;
					start = 2;
				} /*
				else if(timeOfDay[0] == 12){ //second boss fight
					System.out.println("An enemy Servant has appeared! They are Hercules, of the Berserker class.");
					bossName = "Berserker";
					bossHP = 1000;
					start = 2;
				}
				else if(timeOfDay[0] == 20){ //third boss fight
					System.out.println("An enemy Servant has appeared! They are Medea, of the Caster class.");
					bossName = "Caster";
					bossHP = 5000;
					start = 2;
				}
				else if(timeOfDay[0] == 26){ //fourth boss fight
					System.out.println("An enemy Servant has appeared! They are Cu Chulainn, of the Lancer class.");
					bossName = "Lancer";
					bossHP = 5000;
					start = 2;
				}
				else if(timeOfDay[0] == 32){ //fifth boss fight
					System.out.println("An enemy Servant has appeared! They are Sasaski Kojirou, of the Assassin class.");
					bossName = "Assassin";
					bossHP = 3000;
					start = 2;
				}
				else if(timeOfDay[0] == 40){ //sixth boss fight
					System.out.println("An enemy Servant has appeared! They are Gilgamesh, of the Archer class.");
					bossName = "Gilgamesh";
					bossHP = 8000;
					start = 2;
				} */
			}
			else{
				System.out.println("You currently have " + playerHP + " HP, " + playerValues[1] + " base ATK, " + localMP[0] + " MP."); //displays player condition
				System.out.println(bossName + " has " + bossHP + " HP."); //displays boss condition
				System.out.println("Attack or defend!");
				String input = s.nextLine();
				String[] attackList = {"attack", "Attack", "kill", "a"};
				boolean attackTrue = false;
				for(String string:attackList){
					if(input.equals(string)){
						attackTrue = true; //return true if it was found
					}
				}
				
				String[] itemList = {"use item" , "kill", "item", "Item"};
				boolean itemTrue = false;
				for(String string:itemList){
					if(input.equals(string)){
						itemTrue = true; //return true if it was found
					}
				}
				
				if(attackTrue){
					System.out.println("You have selected attack.");
					System.out.println("Magic or physical?");
					String usrInput = s.nextLine();
					
					String[] magicList = {"magic", "Magic", "m"};
					for(String string: magicList){
						if(usrInput.equals(string)){
							int mpWeapon = magicAttacking(playerValues, localMP); //calls magic attack method
							bossHP -= mpWeapon; //subtracts the attack from the boss' hp
							if(bossHP <= 0){ //if boss hp is less than or equal to 0 you win
								if(timeOfDay[0] == 40){ //if you win the last fight
									System.out.println("You are the champion of the Sixth Holy Grail War!");
									System.out.println("You can now have your wish granted! :)");
									System.out.println("Thank you for playing!");
									System.exit(0);
									localMP[0] = playerValues[2];
									timeOfDay[0] ++;
									return 0;
								}
								else{ //if you win a regular fight
									System.out.println("You win the fight! Congratulations. You will resume your action.");
									localMP[0] = playerValues[2];
									timeOfDay[0] ++; //continues the time
									return 0;
								}
							}
							else{ //if boss is not dead, the boss attacks depending on which boss
								if(timeOfDay[0] == 5){
									int riderAttacks = riderATK();
									playerHP -= riderAttacks;
								}
								/* else if(timeOfDay[0] == 12){
									int berserkerAttacks = berserkerATK();
									playerHP -= berserkerAttacks;
								}
								else if(timeOfDay[0] == 20){
									int casterAttacks = casterATK();
									playerHP -= casterAttacks;
								}
								else if(timeOfDay[0] == 26){
									int lancerAttacks = lancerATK();
									playerHP -= lancerAttacks;
								}
								else if(timeOfDay[0] == 32){
									int assassinAttacks = assassinATK();
									playerHP -= assassinAttacks;
								}
								else if(timeOfDay[0] == 40){
									int archerAttacks = archerATK();
									playerHP -= archerAttacks;
								} */
								if (playerHP <= 0){ //if the boss kills you
									System.out.println("You have died. The game will restart.");
									gameEnd[0] = true; //ends the game
									loop = 0; //breaks the loop
								}
							}
						}
					}
					String[] physicalList = {"physical", "Physical", "p"};
					for(String string: physicalList){
						if(usrInput.equals(string)){ 
							int atkWeapon = physicalAttacking(take, playerValues); //calls the physical attack method
							System.out.println("You do " + (atkWeapon * playerValues[1]) + " damage."); //multiplies the attack from the method and base attack for total attack
							bossHP -= (atkWeapon * playerValues[1]); //subtracts the total attack from the boss HP
							if(bossHP <= 0){ //if boss hp is less than or equal to 0 you win
								if(timeOfDay[0] == 40){ //if the final fight is won
									System.out.println("You are the champion of the Sixth Holy Grail War!");
									System.out.println("You can now have your wish granted! :)");
									System.out.println("Thank you for playing!");
									System.exit(0); //ends the game
									localMP[0] = playerValues[2];
									timeOfDay[0] ++;
									return 0;
								}
								else{ //if a normal fight is won
									System.out.println("You win the fight! Congratulations. You will resume your action.");
									localMP[0] = playerValues[2];
									timeOfDay[0] ++; //continues the time
									return 0;
								}
							}
							else{ //if boss is not dead, the boss attacks depending on which boss
								if(timeOfDay[0] == 5){
									int riderAttacks = riderATK();
									playerHP -= riderAttacks;
								}
								/* else if(timeOfDay[0] == 12){
									int berserkerAttacks = berserkerATK();
									playerHP -= berserkerAttacks;
								}
								else if(timeOfDay[0] == 20){
									int casterAttacks = casterATK();
									playerHP -= casterAttacks;
								}
								else if(timeOfDay[0] == 26){
									int lancerAttacks = lancerATK();
									playerHP -= lancerAttacks;
								}
								else if(timeOfDay[0] == 32){
									int assassinAttacks = assassinATK();
									playerHP -= assassinAttacks;
								}
								else if(timeOfDay[0] == 40){
									int archerAttacks = archerATK();
									playerHP -= archerAttacks;
								} */
								if (playerHP <= 0){
									System.out.println("You have died. The game will restart.");
									gameEnd[0] = true;
									loop = 0;
								}
							}
						}
					}
				}
				/* else if(itemTrue){ //if player selected to use an item
					System.out.println("You have selected to use an item.");
					System.out.println("Would you like to use a sandwich or a potion?");
					String newInput = s.nextLine();
					for(String string: sandwichList){
						if(newInput.equals(string)){ //if they use a sandwich
							int hpRestore = usingSandwich(playerValues); //calls method
							playerHP += hpRestore; //restores hp depending on returned value
							//boss then attacks
							if(timeOfDay[0] == 5){
									int riderAttacks = riderATK();
									playerHP -= riderAttacks;
								}
								else if(timeOfDay[0] == 12){
									int berserkerAttacks = berserkerATK();
									playerHP -= berserkerAttacks;
								}
								else if(timeOfDay[0] == 20){
									int casterAttacks = casterATK();
									playerHP -= casterAttacks;
								}
								else if(timeOfDay[0] == 26){
									int lancerAttacks = lancerATK();
									playerHP -= lancerAttacks;
								}
								else if(timeOfDay[0] == 32){
									int assassinAttacks = assassinATK();
									playerHP -= assassinAttacks;
								}
								else if(timeOfDay[0] == 40){
									int archerAttacks = archerATK();
									playerHP -= archerAttacks;
								}
							if (playerHP <= 0){ //if the boss kills the player
								System.out.println("You have died. The game will restart.");
								gameEnd[0] = true; //ends the game
								loop = 0;
							}
						}
					}
					for(String string: potionList){ //if the player selected to use a potion
						if(newInput.equals(string)){
							int mpRestore = usingPotion(playerValues); //calls method
							localMP[0] += mpRestore; //restores MP based on returned value
							//boss attacks
							if(timeOfDay[0] == 5){
									int riderAttacks = riderATK();
									playerHP -= riderAttacks;
								}  /*
								else if(timeOfDay[0] == 12){
									int berserkerAttacks = berserkerATK();
									playerHP -= berserkerAttacks;
								}
								else if(timeOfDay[0] == 20){
									int casterAttacks = casterATK();
									playerHP -= casterAttacks;
								}
								else if(timeOfDay[0] == 26){
									int lancerAttacks = lancerATK();
									playerHP -= lancerAttacks;
								}
								else if(timeOfDay[0] == 32){
									int assassinAttacks = assassinATK();
									playerHP -= assassinAttacks;
								}
								else if(timeOfDay[0] == 40){
									int archerAttacks = archerATK();
									playerHP -= archerAttacks;
								}
							if (playerHP <= 0){ //if boss kills player
								System.out.println("You have died. The game will restart.");
								gameEnd[0] = true; //ends game
								loop = 0;
							}
						}
					}
				}
				else{ //if user tries to input something else
					System.out.println("That's not an option!");
				} */
			}
		} 
		return loop;
	}
} 