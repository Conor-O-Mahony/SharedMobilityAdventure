
package sharedMobilityAdventure;

// An interface for behaviors that vary among transportation types
interface CarbonFootprintCalculator {
    double calculateCarbonFootprint(int distance); // Calculate carbon footprint based on distance from route class
}

// Enum definition with additional properties and implementing an interface reference: https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html / https://www.baeldung.com/a-guide-to-java-enums
// TransportType(CC factor, speed, max congestion factor))
public enum TransportTypes implements CarbonFootprintCalculator {
    BUS(10.0, 5.0, 1.4) {
    	private double calculatedSpeed = -1;  //set as invalid value to indicate value is not calculated - this is as otherwise the speed is calculated as part of the loop and it can flash a variation of 3-4 units due to the calculation method of random congestion

        @Override
        public double getSpeed() {
            if (calculatedSpeed == -1) {  // Check if the speed has not been calculated yet
                // Calculate speed 
            	calculatedSpeed = speed + (Math.random() * (maxCongestionFactor - 1)) + 1;
		// Reference: https://stackoverflow.com/questions/64714783/java-generate-random-numbers-between-minimum-and-a-maximum-values
            }
            return calculatedSpeed;
        }
    },
    TRAIN(6.0, 5.0, 1),
    BICYCLE(2.0, 25.0, 1);

    protected final double carbonCoinFactor; 
    protected final double speed;            
    protected final double maxCongestionFactor; 

    TransportTypes(double carbonCoinFactor, double speed, double maxCongestionFactor) {
        this.carbonCoinFactor = carbonCoinFactor;
        this.speed = speed;
        this.maxCongestionFactor = maxCongestionFactor;
    }

    public double calculateCarbonFootprint(int distance) {
        return this.carbonCoinFactor * distance;
    }

    public double getSpeed() {
        return this.speed;
    }

    public double getCarbonCoinFactor() {
        return this.carbonCoinFactor;
    }

    public double getMaxCongestionFactor() {
        return this.maxCongestionFactor;
    }
}
