import java.util.Arrays;
import java.util.Random;

public class ANN {

    // All input sets
    int[][] trainingExamples = {{1,1,1,1},{1,1,1,-1},{1,1,-1,1},{1,1,-1,-1},{1,-1,1,1},{1,-1,1,-1},{1,-1,-1,1},{1,-1,-1,-1},{-1,1,1,1},{-1,1,1,-1},{-1,1,-1,1},{-1,1,-1,-1},{-1,-1,1,1},{-1,-1,1,-1},{-1,-1,-1,1},{-1,-1,-1,-1}};

    // This method converts an array into two dimensional array to one dimensional array
    public int[] twoToOne(int[][] arr, int index){
        int [] oneArr = new int[4];
        for (int i = 0; i < oneArr.length; i++){
            oneArr[i] = arr[index][i];
        }
        return oneArr;
    }

    // This method creates randomly 4 weights between -0.5 and 0.5.
    public double[] inputWeights(){
        double[] weights = new double[4];
        Random random = new Random(  );
        for (int i = 0; i <weights.length; i++){
            double n = random.nextDouble() - 0.5;
            weights[i] = n;
        }
        return weights;
    }

    // This method creates randomly 2 weights between -0.5 and 0.5.
    public double[] hiddenWeights(){
        double[] weights = new double[2];
        Random random = new Random(  );
        for (int i = 0; i <weights.length; i++){
            double n = random.nextDouble() - 0.5;
            weights[i] = n;
        }
        return weights;
    }

    // This method gets the neurons and the weights, then multiplies neurons
    // with corresponding weights. Afterward applies sigmoid function and returns it.
    public double hiddenOutput(double hid1, double hid2, double[] hidw){
        double S = hidw[0]*hid1 + hidw[1]*hid2;
        return 1/(1+Math.pow( Math.E, -S ));
    }

    // This method gets the neurons and the weights, then multiplies neurons
    // with corresponding weights. Afterward applies sigmoid function and returns it.
    public double inputOutput(int[] image, double[] weights){
        double S = 0, output;
        for (int i = 0; i<weights.length; i++){
            S += weights[i]*image[i];
        }
        output = 1/(1+Math.pow( Math.E, -S ));
        return output;
    }

    // This method gets weights, neurons, learning rate, target output, and observed output.
    // Creates error values for both hidden and output units.
    // Then updates weights of both hidden and output units.
    public void updWeights(double[] inp1, double[] inp2, double[] hidw1, double[] hidw2, int[]image, double learningRate, double[] target, double out1, double out2, double hid1, double hid2){
        // Required variables created
        double[] delta = new double[4];
        double eOut1, eOut2, eHid1, eHid2;
        // Calculate the error values of output units
        eOut1 = out1*(1-out1)*(target[0]-out1);
        eOut2 = out2*(1-out2)*(target[1]-out2);
        // Calculate the error values of hidden units are calculated
        eHid1 = ((hidw1[0]*eOut1)+(hidw2[0]*eOut2))*(hid1*(1-hid1));
        eHid2 = ((hidw1[1]*eOut1)+(hidw2[1]*eOut2))*(hid2*(1-hid2));
        // Update the weights of hidden units
        for (int i = 0; i<delta.length; i++){
            delta[i] = learningRate*eHid1*image[i];
        }
        for (int i = 0; i<delta.length; i++){
            inp1[i] = inp1[i] + delta [i];
        }
        for (int i = 0; i<delta.length; i++){
            delta[i] = learningRate*eHid2*image[i];
        }
        for (int i = 0; i<delta.length; i++){
            inp2[i] = inp2[i] + delta [i];
        }
        // Update the weights of output units
        hidw1[0] = hidw1[0] + (learningRate*eOut1*hid1);
        hidw1[1] = hidw1[1] + (learningRate*eOut1*hid2);
        hidw2[0] = hidw2[0] + (learningRate*eOut2*hid1);
        hidw2[1] = hidw2[1] + (learningRate*eOut2*hid2);
    }

    //This method gets the image and check whether it is BRIGHT or DARK.
    //If image contains 2, 3, or 4 white pixels, the output is BRIGHT,
    //If it contains 0 or 1 white pixels, it is DARK.
    public double[] observe(int[]image){
        int sum = 0;
        for (int i = 0; i<image.length; i++){
            sum += image[i];
        }
        if (sum == 0 || sum == 2 || sum == 4){
            return new double[]{1.0, 0.0};
        }else {
            return new double[]{0.0, 1.0};
        }
    }

    //This method executes back propogation training, only gets the learning rate.
    public void backpropogation(double lRate){
        // Creating the required variables
        int[] image;
        double[] inp1, inp2, hidw1, hidw2, target, result;
        double hid1, hid2, out1, out2;
        int epochs = 0, count = 0;
        // Setting the initial random weights
        inp1 = new double[]{-0.37,-0.42,-0.18,0.14};
        inp2 = new double[]{0.49,-0.31,-0.24,-0.07};
        hidw1 = new double[]{-0.14,-0.03};
        hidw2 = new double[]{0.14,-0.07};
        //System.out.println( Arrays.toString(inp1));
        //System.out.println( Arrays.toString(inp2));
        //System.out.println( Arrays.toString(hidw1));
        //System.out.println( Arrays.toString(hidw2));
        // Starting the cycle, until all inputs are trained correctly
        while (count != 16){
            count = 0;
            for (int i = 0; i<16; i++){
                // Gets the first input set
                image = twoToOne( trainingExamples, i );
                // Checks the target output
                target = observe( image );
                // Calculates the outputs of both hidden and output units
                hid1 = inputOutput( image, inp1 );
                hid2 = inputOutput( image, inp2 );
                out1 = hiddenOutput( hid1, hid2, hidw1 );
                out2 = hiddenOutput( hid1, hid2, hidw2 );
                // Calculates the result from output units
                result = new double[]{out1, out2};
                // If the result is close enough the target, second input is trained
                if ((result[0] > 0.95 && result[1] < 0.2) || (result[0] < 0.2 && result[1] > 0.95)){
                    count++;
                }
                // Otherwise, weights are updated
                else {
                    updWeights( inp1, inp2, hidw1, hidw2, image, lRate, target, out1, out2, hid1, hid2);
                }
            }
            epochs++;
        }
        System.out.println();
        System.out.println("Backpropogation algorithm has been finished successfully.\n" + epochs + " epochs are operated.");
    }

    // Testing the algorithm
    public static void main(String[] args) {
        ANN ann = new ANN();
        ann.backpropogation(0.2);
    }
}
