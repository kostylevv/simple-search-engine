package simpleSearchEngine;

import java.util.*;

class Main {

  public static void main(String args[]) {
    List<Person> persons = new ArrayList<>();
    Scanner sc = new Scanner(System.in);
    System.out.println("Enter persons data in format: first name | last name | email (optional). Each entry from a new line, delimit with spaces. To terminate enter /stop");
    while(true) {
      String input = sc.nextLine();
      if (input.trim().equals("/stop")) break;
      if (input != null && input.length() > 0) {
        String words[] = input.trim().split(" ");
        if (words.length >= 2) {
          String lastName = words[1];
          String firstName = words[0];
          String email = (words.length == 3) ? words[2] : null;
          Person person = new Person(firstName, lastName, email);
          persons.add(person);
        } else {
          System.out.println("First name and last name are requied params");
        }
      }
    }
    for (Person person : persons) {
      Finder.addPerson(person);
    }

    System.out.println("Enter search term. To terminate enter /stop");
    while(true) {
      String input = sc.nextLine();
      if (input.trim().equals("/stop")) break;
      if (input != null && input.length() > 0) {
        if (Finder.find(input.trim()) != null) {
            System.out.println("Found people: ");
            for (Person person : Finder.find(input)) {
                System.out.println(person.toString());
            }
        } else {
            System.out.println("Not found");
        }
      }
    }
  }
}

  class Person {

    public Person(String firstName, String lastName, String email) {

      if (firstName != null) this.firstName = firstName.trim();
      if (lastName != null) this.lastName = lastName.trim();
      if (email != null) this.email = email.trim();

    }

    public String toString() {
      if (email != null) {
        return firstName + " " + lastName + " " + email;
      } else {
        return firstName + " " + lastName;
      }
    }

    public List<String> getFields() {
      List<String> result = new ArrayList<>();
      if (firstName != null) result.add(firstName);
      if (lastName != null) result.add(lastName);
      if (email != null) result.add(email);
      return result;
    }

    private String firstName;
    private String lastName;
    private String email;
  }

  class Finder {

    private Finder(){};

    public static void addPerson(Person person) {
      List<String> fields = person.getFields();
      for (String field : fields) {
        if (mapping.containsKey(field.toLowerCase())) {
          Set<Person> persons = mapping.get(field.toLowerCase());
          persons.add(person);
        } else {
          Set<Person> persons = new HashSet<>();
          persons.add(person);
          mapping.put(field.toLowerCase(), persons);
        }
      }
    }

    public static Set<Person> find(String term) {
      if (mapping.containsKey(term.trim().toLowerCase())) {
        return mapping.get(term.trim().toLowerCase());
      } else return null;

    }

  private static Map<String, Set<Person>> mapping = new HashMap<>();

}

/*
javac src/com/kostylevv/paksrms/*.java
java -cp src/ com.kostylevv.paksrms.Paks
*/
