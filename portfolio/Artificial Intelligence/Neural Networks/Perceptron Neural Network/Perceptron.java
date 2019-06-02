import java.util.Random;

public class Perceptron {

    //All input sets
    int[][] trainingExamples = {{1,1,1,1},{1,1,1,-1},{1,1,-1,1},{1,1,-1,-1},
    {1,-1,1,1},{1,-1,1,-1},{1,-1,-1,1},{1,-1,-1,-1},{-1,1,1,1},{-1,1,1,-1},
    {-1,1,-1,1},{-1,1,-1,-1},{-1,-1,1,1},{-1,-1,1,-1},{-1,-1,-1,1},{-1,-1,-1,-1}};

    //This method converts an array into two dimensional array to one dimensional array
    public int[] twoToOne(int[][] arr, int index){
        int [] oneArr = new int[4];
        for (int i = 0; i < oneArr.length; i++){
            oneArr[i] = arr[index][i];
        }
        return oneArr;
    }

    //This method creates randomly 4 weights between 0 and 1.
    public double[] randomWeights(){
        double[] weights = new double[4];
        Random random = new Random(  );
        for (int i = 0; i <weights.length; i++){
            double n = random.nextDouble();
            weights[i] = n;
        }
        return weights;
    }

    //This method gets the neurons and the weights, then multiplies neurons
    //with corresponding weights. Returns the summation of that.
    public double propagator(int[] image, double[] weights){
        double S = 0;
        for (int i = 0; i<image.length; i++){
            S += image[i]*weights[i];
        }
        return S;
    }

    //This method gets threshold value and the summation of weighted neurons.
    //Check the sum compare to threshold value and decide if it is BRIGHT or DARK.
    public int stepFunction(double threshold, double sum){
        if (sum > threshold) return 1;
        else return -1;
    }

    //This method gets weights, neurons, learning rate, target output, and observed output.
    //Firstly creates deltas by calculating as D1 = LearningRate*(TO-OO)N1
    //Then updates the weights with adding deltas on them.
    public void updateWeights(double[] weights, int[]image, double learningRate, int tE, int oE){
        double[] deltas = new double[4];
        for (int i = 0; i<deltas.length; i++){
            deltas[i] = learningRate*(tE-oE)*image[i];
        }
        for (int i = 0; i<weights.length; i++){
            weights[i] = weights[i] + deltas [i];
        }
    }

    //This method gets the image and check whether it is BRIGHT or DARK.
    //If image contains 2, 3, or 4 white pixels, the output is BRIGHT,
    //If it contains 0 or 1 white pixels, it is DARK.
    public int target(int[]image){
        int sum = 0;
        for (int i = 0; i<image.length; i++){
            sum += image[i];
        }
        if (sum == 0 || sum == 2 || sum == 4){
            return 1;
        }else return -1;
    }

    //This method prints image by getting image array as dark and white blocks.
    public void printImage(int[] image){
        for (int i = 0; i<2; i++){
            if (image[i] == 1) System.out.print("■");
            else System.out.print("□");
        }
        System.out.println();
        for (int i = 2; i<image.length; i++){
            if (image[i] == 1) System.out.print("■");
            else System.out.print("□");
        }
        System.out.println();
    }

    //This method executes perceptron training, only gets threshold and learning rate.
    public void perceptronTraining(double threshold, double learningRate){
        //Variables
        int[] image;
        double[] weights;
        double sum;
        int result, epochs = 0, target, count = 0;
        //Creates random weights
        weights = randomWeights();
        //Algorithm will continue until all inputs are guessed correctly
        while (count != 16){
            count = 0;
            for (int i = 0; i<16; i++){
            //Gets the training examples
            image = twoToOne( trainingExamples, i );
            //Calculates the actual output
            target = target( image );
            //Calculates the sum by getting neurons and weights
            sum = propagator( image, weights );
            //Applies step function to find out the result
            result = stepFunction( threshold, sum );
            //If the result is equal to the target, the method reveals a message
            if (result == target){
                if (target == 1) {
                    printImage( image );
                    System.out.println("Perceptron Neural Network algorithm has been successful.\nThe image is BRIGHT");
                }
                else {
                    printImage( image );
                    System.out.println("Perceptron Neural Network algorithm has been successful.\nThe image is DARK");
                }
                count++;
            }
            //If the result is not equal to the target, it updates the weights
            else {
                updateWeights( weights,image,learningRate,target,result );
                if (target == 1) {
                    printImage( image );
                    System.out.println("Fail!\nThe image is BRIGHT but the estimation is DARK");
                }
                else {
                    printImage( image );
                    System.out.println("Fail!\nThe image is DARK but the estimation is BRIGHT");
                }
            }
        }
        epochs++;
        }
        System.out.println();
        System.out.println("Algorithm has been finished.\n" + epochs + " epochs are operated.");
    }

    //Testing the algorithm
    public static void main(String[] args) {
        Perceptron perceptron = new Perceptron();
        perceptron.perceptronTraining( -0.1, 0.01 );
    }
}
