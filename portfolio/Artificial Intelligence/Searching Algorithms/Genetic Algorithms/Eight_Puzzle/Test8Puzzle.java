import java.util.*;

public class Test8Puzzle {

    public static void main(String[] args) {
        Test8Puzzle puzzle = new Test8Puzzle();
        int[] goal = {1,2,3,8,0,4,7,6,5};
        puzzle.geneticAlgo(10,0.7 ,0.2, goal);
    }

    //Creation of random choromosoms
    public int[] randomArray(){
        Integer [] arr = new Integer[9];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        Collections.shuffle( Arrays.asList(arr));
        return Arrays.stream(arr).mapToInt(Integer::intValue).toArray();
    }

    //Mutation of the choromosom
    public int[] swapMutation(int[] parent){
        int[] array = parent.clone();
        int l = array.length;
        Random random = new Random();
        //get 2 random integers between 0 and size of array
        int r1 = random.nextInt(l-1);
        int r2 = random.nextInt(l-1);
        //to make sure the 2 numbers are different
        while(r1 == r2) r2 = random.nextInt(l-1);

        //swap array elements at those indices
        int temp = array[r1];
        array[r1] = array[r2];
        array[r2] = temp;

        return array;
    }

    //Crossover of random choromosoms
    public int[] reproduce(int[] father, int[] mother) {
        int[] child = new int[father.length];
        Random random = new Random();
        int crossPoint = random.nextInt(8);
        for (int i=0;i<crossPoint;i++)
        {
            child[i]=father[i];
        }
        int j=crossPoint;
        for (int i=0;i<father.length;i++){
            for(int k=0;k<crossPoint;k++){
                if(mother[i] == child[k]){
                    mother[i] = 10;
                }
            }
            if(mother[i] != 10){
                child[j] = mother[i];
                j++;
            }
        }
        return child;
    }

    //The function that execute the mutation
    public void doMutation(int[][] choromosomes, int pick){
        int[] a = swapMutation(twoDtoOneD( choromosomes,pick ));
        for (int i = 0; i < a.length; i++){
            choromosomes[pick][i] = a[i];
        }
        choromosomes[pick][9] = fitness( twoDtoOneD( choromosomes,pick ) );
    }

    //The function that execute the crossover
    public void doCrossover(int[][] choromosomes, int pick1, int pick2){
        int[] a = reproduce(twoDtoOneD( choromosomes,pick1), twoDtoOneD( choromosomes,pick2));
        for (int i = 0; i < a.length; i++){
            choromosomes[pick1][i] = a[i];
        }
        choromosomes[pick1][9] = fitness( twoDtoOneD( choromosomes,pick1 ) );
    }

    //Convertion function from two dimensional array to one dimensional array
    public int[] twoDtoOneD(int[][] twoD, int index){
        int[] oneD = new int[9];
        for (int i = 0; i < oneD.length; i++){
            oneD[i] = twoD[index][i];
        }
        return oneD;
    }

    //Finding fitness value of the chromosome
    public int fitness(int[] arr){
        if (arr != null) {
            int fitValue = 0;
            for (int i = 0; i < arr.length; i++) {
                fitValue += arr[i] * (i + 1);
            }
            return fitValue;
        }
        return 0;
    }

    //Finding how a chromosome close to the goal
    public int isCloseTo(int[] goal, int[] arr) {
        int fitnessValue = fitness( arr );
        if (fitnessValue > fitness( goal )) {
            return 2000 - (fitnessValue - fitness( goal ));
        } else if (fitnessValue < fitness( goal )) {
            return 2000 - (fitness( goal ) - fitnessValue);
        } else return 2000;
    }

    //Finding the closest chromosome whether equal to the goal
    public boolean solution(int[] goal, int[] arr){
        int[] a = new int[goal.length];
        int sum = 1;
        for (int i = 0; i <goal.length; i++){
            if (goal[i]==arr[i]) {
                a[i] = 1;
            }else a[i] = 0;
        }
        for (int i = 0; i <goal.length; i++) {
            sum = sum * a[i];
        }
        if (0 != sum){
            return true;
        }else return false;
    }

    //Function of genetic Algorithm
    public void geneticAlgo(int population, double pc, double pm, int[] goal){
        Random random = new Random();
        float k = random.nextFloat(); // Random number for doing
        int pick1, pick2 = 0;
        int[][] chromosomes = new int[population][10]; //Creating the population
        for (int i = 0; i < population; i++){ //Creating choromosomes
            int[] arr = randomArray();
            for (int j = 0; j < 9; j++) {
                chromosomes[i][j] = arr[j];
            }
            chromosomes[i][9] = fitness( twoDtoOneD( chromosomes,i ) );
        }
        for (int i = 0; i < population/2; i++){ //Doing crossover
            pick1 = random.nextInt( population-1);
            pick2 = random.nextInt( population-1);
            if (k > pc){
                doCrossover( chromosomes,pick1,pick2 );
            }else break;
        }
        for (int i = 0; i < population/2; i++){ //Doing mutation
            pick1 = random.nextInt( population-1);
            if (k > pm){
                doMutation( chromosomes,pick1 );
            }else break;
        }
        for (int i = 0; i < population; i++){
            chromosomes[i][9] = isCloseTo( goal,twoDtoOneD( chromosomes,i ) );
        }
        java.util.Arrays.sort( chromosomes, new Comparator<int[]>() { // Ranking chromosomes according to their fitness
            @Override
            public int compare(int[] o1, int[] o2) {
                return Integer.compare( o1[9],o2[9] );
            }
            @Override
            public boolean equals(Object obj) {
                return false;
            }
        } );
        if (solution( goal, twoDtoOneD( chromosomes,population -1) )){
            System.out.println(Arrays.toString( twoDtoOneD( chromosomes,population-1 ) ));
            System.out.println("1  " + fitness( twoDtoOneD( chromosomes,population-1 )));
            return;
        }else{
            while (!solution( goal, twoDtoOneD( chromosomes, population - 1 ) )){//Repeating till find the solution
                for (int i = 0; i < population/2; i++){
                    int[] arr = randomArray();
                    for (int j = 0; j < 9; j++) {
                        chromosomes[i][j] = arr[j];
                    }
                    chromosomes[i][9] = fitness( twoDtoOneD( chromosomes,i ) );
                }
                for (int i = 0; i < population/2; i++){
                    pick1 = random.nextInt( population-1);
                    pick2 = random.nextInt( population-1);
                    if (k > pc){
                        doCrossover( chromosomes,pick1,pick2 );
                    }else break;
                }
                for (int i = 0; i < population/2; i++){
                    pick1 = random.nextInt( population-1);
                    if (k > pm){
                        doMutation( chromosomes,pick1 );
                    }else break;
                }
                for (int i = 0; i < population; i++){
                    chromosomes[i][9] = isCloseTo( goal,twoDtoOneD( chromosomes,i ) );
                }
                java.util.Arrays.sort( chromosomes, new Comparator<int[]>() {
                    @Override
                    public int compare(int[] o1, int[] o2) {
                        return Integer.compare( o1[9],o2[9] );
                    }

                    @Override
                    public boolean equals(Object obj) {
                        return false;
                    }
                } );
            }
            System.out.println(Arrays.toString( twoDtoOneD( chromosomes,population-1 ) ));
            System.out.println("2  " + fitness( twoDtoOneD( chromosomes,population-1 )));
        }
    }
}
