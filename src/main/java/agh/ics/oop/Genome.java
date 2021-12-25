package agh.ics.oop;

import java.util.Arrays;
import java.util.Random;

public class Genome {
    private final int[] genes = new int[32];
    private Random rand = new Random();

    public Genome(Animal animalA, Animal animalB){
        int[] genomeA = animalA.getGenome().getGenes();
        int[] genomeB = animalB.getGenome().getGenes();


        int divide = (int) Math.round( 31.0 * (double) animalA.getEnergy() / (double) (animalA.getEnergy() + animalB.getEnergy()));
        for (int i=0; i<divide; i++){
           this.genes[i] = genomeA[i];
        }

        for (int i=divide; i<32; i++){
            this.genes[i] = genomeB[i];
        }
        Arrays.sort(this.genes);
    }

    public Genome(){
        for (int i=0; i<32; i++){
            this.genes[i] = rand.nextInt(8);
        }
        Arrays.sort(this.genes);
    }

    public int[] getGenes() {
        return genes;
    }

    public int getTurn(){
        return this.genes[this.rand.nextInt(8)];
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int v : this.genes){
            result.append(v);
        }
        return result.toString();
    }
}
