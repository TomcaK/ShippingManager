package cz.comkop.shipingmanager;


public class Trailer {
    private final TrailerTemplate template;

    private final String[][] trailerModel;

    private char nextCodename = 'A';
    private double freeSquareCentimeters;
    private double LDM, freeLDM;
    private int totalWeight;


    public Trailer(TrailerTemplate template) {
        this.template = template;
        freeSquareCentimeters = template.getLength() * template.getWidth();
        trailerModel = new String[template.getLength()][template.getWidth()];
        for (int i = 0; i < trailerModel.length; i++) {
            for (int j = 0; j < trailerModel[0].length; j++) {
                char freeSpaceChar = '-';
                if (trailerModel[i][j] != null) {
                    trailerModel[i][j] += freeSpaceChar;
                } else {
                    trailerModel[i][j] = String.valueOf(freeSpaceChar);
                }
            }
        }
    }

    public void setFreeSquareCentimeters(double freeSquareCentimeters) {
        this.freeSquareCentimeters = freeSquareCentimeters;
    }

    public void setTotalWeight(int totalWeight) {
        this.totalWeight = totalWeight;
    }

    public char getNextCodename() {
        return nextCodename;
    }

    public void setNextCodename(char nextCodename) {
        this.nextCodename = nextCodename;
    }


//

    public double getLDM() {
        return LDM;
    }

    /**
     * Counts total length used in trailer
     **/
    public void countLDM() {
        double count = 0;
        for (String[] strings : trailerModel) {
            for (int j = 0; j < trailerModel[0].length; j++) {
                if (!strings[j].equals("-")) {
                    count++;
                    break;
                }
            }
        }
        LDM = count / 100;
        freeLDM = (double) template.getLength() / 100 - LDM;
    }


    public int getTotalWeight() {
        return totalWeight;
    }


    public void printOutlineOfTrailer() {//TODO new outline method without chars
        String usedCharnames = "";
        String foundCharnames = "";
        int count = 0;
        for (String[] strings : trailerModel) {
            for (int j = 0; j < trailerModel[0].length; j++) {
                if (!usedCharnames.contains(strings[j]) && !strings[j].equals("-")) {
                    usedCharnames += strings[j];
                    foundCharnames += strings[j];
                    count++;
                }
            }
            if (count > 0) {
                foundCharnames += "\n";
                count = 0;
            }
        }
        System.out.println(foundCharnames);
    }


    public String[][] getTrailerModel() {
        return trailerModel;
    }

    public double getFreeSquareCentimeters() {
        return freeSquareCentimeters;
    }

    public TrailerTemplate getTemplate() {
        return template;
    }



    @Override
    public String toString() {
        return "Needed LDM: " + LDM + " m, total weight: " + totalWeight + " kg";
    }
}