package de.jofre.test;

public class NamesByGender {

    /**
     * Ermittle das Geschlecht anhand eines Vornamens.
     *
     * @param _name
     * @return
     */
    public static String getGender(final String _name) {

        if (contains(_name, MALE_NAMES)) {
            return "male";
        }
        if (contains(_name, FEMALE_NAMES)) {
            return "female";
        }
        return "unknown";
    }

    /**
     * Wähle einen zufälligen Vornamen.
     *
     * @return
     */
    public static java.lang.String getRandomName() {
        final java.util.Random r = new java.util.Random();
        final int gender = r.nextInt(2);
        if (gender == 0) {
            // Männlich
            final int index = r.nextInt(MALE_NAMES.length);
            return MALE_NAMES[index];
        } else {
            // Weiblich
            final int index = r.nextInt(FEMALE_NAMES.length);
            return FEMALE_NAMES[index];
        }
    }

    /**
     * Liefere einen zufälligen Nachnamen.
     *
     * @return
     */
    public static java.lang.String getRandomLastName() {
        final java.util.Random r = new java.util.Random();
        final int index = r.nextInt(LAST_NAMES.length);
        return LAST_NAMES[index];
    }

    private static boolean contains(final java.lang.String _str, final java.lang.String[] _list) {
        for (final String element : _list) {
            if (element.equalsIgnoreCase(_str)) {
                return true;
            }
        }
        return false;
    }

    public final static java.lang.String[] LAST_NAMES = { "Meier",
            "Möller",
            "Schmidt",
            "Schneider",
            "Fischer",
            "Weber",
            "Wagner",
            "Becker",
            "Schulz",
            "Hoffmann",
            "Schäfer",
            "Koch",
            "Bauer",
            "Richter",
            "Klein",
            "Wolf",
            "Schröder",
            "Neumann",
            "Schwarz",
            "Zimmermann",
            "Braun",
            "Krüger",
            "Hartmann",
            "Lange",
            "Schmitt",
            "Krause",
            "Lehmann",
            "Schulze",
            "Köhler",
            "Herrmann",
            "König",
            "Huber",
            "Fuchs",
            "Peters",
            "Müller",
            "Weiß",
            "Jung",
            "Hahn",
            "Schubert",
            "Vogel",
            "Friedrich",
            "Keller",
            "Günther",
            "Frank",
            "Berger",
            "Winkler",
            "Roth",
            "Beck",
            "Lorenz",
            "Baumann",
            "Albrecht",
            "Schuster",
            "Simon",
            "Ludwig",
            "Böhm",
            "Winter",
            "Martin",
            "Schuhmacher",
            "Krämer" };

    public final static java.lang.String[] MALE_NAMES = { "Alexander",
            "Andreas",
            "Benjamin",
            "Bernd",
            "Christian",
            "Daniel",
            "David",
            "Dennis",
            "Dieter",
            "Dirk",
            "Dominik",
            "Eric",
            "Erik",
            "Felix",
            "Florian",
            "Frank",
            "Jan",
            "Jens",
            "Jonas",
            "Jörg",
            "Jürgen",
            "Kevin",
            "Klaus",
            "Kristian",
            "Christian",
            "Leon",
            "Lukas",
            "Marcel",
            "Marco",
            "Marko",
            "Mario",
            "Markus",
            "Martin",
            "Mathias",
            "Matthias",
            "Max",
            "Maximilian",
            "Michael",
            "Mike",
            "Maik",
            "Niklas",
            "Patrick",
            "Paul",
            "Peter",
            "Philipp",
            "Phillipp",
            "Ralf",
            "Ralph",
            "Rene",
            "Robert",
            "Sebastian",
            "Stefan",
            "Stephan",
            "Steffen",
            "Sven",
            "Swen",
            "Thomas",
            "Thorsten",
            "Torsten",
            "Tim",
            "Tobias",
            "Tom",
            "Ulrich",
            "Uwe",
            "Wolfgang" };

    public final static java.lang.String[] FEMALE_NAMES = { "Andrea",
            "Angelika",
            "Anja",
            "Anke",
            "Anna",
            "Anne",
            "Annett",
            "Antje",
            "Barbara",
            "Birgit",
            "Brigitte",
            "Christin",
            "Christina",
            "Christine",
            "Claudia",
            "Daniela",
            "Diana",
            "Doreen",
            "Franziska",
            "Gabriele",
            "Heike",
            "Ines",
            "Jana",
            "Janina",
            "Jennifer",
            "Jessica",
            "Jessika",
            "Julia",
            "Juliane",
            "Karin",
            "Karolin",
            "Katharina",
            "Kathrin",
            "Katrin",
            "Katja",
            "Kerstin",
            "Klaudia",
            "Claudia",
            "Kristin",
            "Christin",
            "Laura",
            "Lea",
            "Lena",
            "Lisa",
            "Mandy",
            "Manuela",
            "Maria",
            "Marie",
            "Marina",
            "Martina",
            "Melanie",
            "Monika",
            "Nadine",
            "Nicole",
            "Petra",
            "Sabine",
            "Sabrina",
            "Sandra",
            "Sara",
            "Sarah",
            "Silke",
            "Simone",
            "Sophia",
            "Sophie",
            "Stefanie",
            "Stephanie",
            "Susanne",
            "Tanja",
            "Ulrike",
            "Ursula",
            "Uta",
            "Ute",
            "Vanessa",
            "Yvonne" };
}
