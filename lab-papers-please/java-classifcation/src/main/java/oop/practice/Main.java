import com.fasterxml.jackson.databind.ObjectMapper;
import oop.practice.lab0.*;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

public static void main(@NotNull String[] args) throws IOException {
  if (args.length == 0) {
    System.out.println("No command-line arguments provided.");
    return; // Exit gracefully to avoid further errors
  }

  System.out.println("First argument: " + args[0]);
  CreatureJsonHandler jsonHandler = new CreatureJsonHandler();
  UniverseManager universeManager = new UniverseManager();
  // Load creatures from JSON file
  File inputFile = new File("C:\\Users\\Intel\\OneDrive\\Desktop\\OOP lab\\lab-papers-please\\java-classifcation\\src\\main\\lab0 descriprion info\\input.json");
  List<Creature> creatures = jsonHandler.loadCreaturesFromJson(inputFile);

  // Initialize universes
  List<Universe> universes = universeManager.initializeUniverses();

  displayMenu(args, creatures, inputFile, universes, universeManager);

  scanner.close(); // Close scanner once at the end
}

private static void displayMenu(String[] args, List<Creature> creatures, File inputFile, List<Universe> universes, UniverseManager universeManager) throws IOException {
  int maxId = creatures.stream().mapToInt(Creature::getId).max().orElse(0);
  String menuChoice = "";

  while (!menuChoice.equals("0")) {
    System.out.println("Menu:");
    System.out.println("1. Display all creatures.");
    System.out.println("2. Add new creature.");
    System.out.println("3. Display creature by id.");
    System.out.println("4. Display creatures with even or odd id.");
    System.out.println("5. Classify creatures into their universes.");
    System.out.println("6. Lab 1");
    System.out.println("0. Exit...");
    System.out.print("Choose a number: ");
    menuChoice = scanner.nextLine();

    switch (menuChoice) {
      case "1":
        for (Creature creature : creatures) {
          creature.displayAllCreatureDetails();
        }
        break;

      case "2":
        Creature newCreature = new Creature(0, false, null, 0, List.of());
        newCreature.addNewCreature(creatures, maxId, inputFile, new ObjectMapper());
        maxId++;
        break;

      case "3":
        System.out.print("Enter the ID of the creature you want to display: ");
        int searchId = Integer.parseInt(scanner.nextLine());
        Creature.displayCreatureById(creatures, searchId);
        break;

      case "4":
        System.out.print("Even or odd (e/o)? ");
        String choice = scanner.nextLine().toLowerCase();
        boolean isEven = choice.equals("e");
        System.out.println(isEven ? "Displaying creatures with even IDs:" : "Displaying creatures with odd IDs:");
        for (Creature creature : creatures) {
          if ((creature.getId() % 2 == 0) == isEven) {
            creature.displayAllCreatureDetails();
          }
        }
        break;

      case "5":
        Universe starWars = universes.get(0);
        Universe hitchhikers = universes.get(1);
        Universe marvel = universes.get(2);
        Universe rings = universes.get(3);
        for (Creature creature : creatures) {
          CreatureClassifier.classifyCreature(creature, starWars, marvel, hitchhikers, rings);
        }
        String outputPath = "src/main/resources/output";
        universeManager.saveUniverses(universes, outputPath);
        System.out.println("Classified creatures and saved universes to JSON files.");
        break;

      case "6":
        menuLab1(args);
        break;
      case "0":
        System.out.println("Exiting program....");
        break;

      default:
        System.out.println("Invalid choice. Please select a valid option.");
    }
  }
}
