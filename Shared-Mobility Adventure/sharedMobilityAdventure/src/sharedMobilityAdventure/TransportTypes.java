package sharedMobilityAdventure;

// An interface for behaviors that vary among transportation types
interface CarbonFootprintCalculator {
    double calculateCarbonFootprint(int distance); // Calculate carbon footprint based on distance from route class
}

// Enum definition with additional properties and implementing an interface reference: https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html / https://www.baeldung.com/a-guide-to-java-enums
// TransportType(CC factor, speed, max congestion factor))
enum TransportTypes implements CarbonFootprintCalculator {
    BUS(10.0, 15.0, 1.4) {
        public double calculateCarbonFootprint(int distance) {
            return getCarbonCoinFactor() * distance;
        }
    },
    TRAIN(6.0, 15.0, 1) {
        public double calculateCarbonFootprint(int distance) {
            return getCarbonCoinFactor() * distance;
        }
    },
    BICYCLE(2.0, 4.0, 1) {
        public double calculateCarbonFootprint(int distance) {
            return getCarbonCoinFactor() * distance;
        }
    },
    CAR(30.0, 15.0, 2) {
        public double calculateCarbonFootprint(int distance) {
            return getCarbonCoinFactor() * distance;
        }
    };

    private final double carbonCoinFactor;
    private final double speed;
    private final double maxCongestionFactor;

    TransportTypes(double carbonCoinFactor, double speed, double maxCongestionFactor) {
        this.carbonCoinFactor = carbonCoinFactor;
        this.speed = speed;
        this.maxCongestionFactor = maxCongestionFactor;
    }

    public double getCarbonCoinFactor() {
        return carbonCoinFactor;
    }

	// Speed is randomised to simulate congestion

	public double getSpeed() {
		return speed + (Math.random() * (getMaxCongestionFactor() - 1)) + 1; // Reference: https://stackoverflow.com/questions/64714783/java-generate-random-numbers-between-minimum-and-a-maximum-values
	}

    public double getMaxCongestionFactor() {
        return maxCongestionFactor;
    }
}