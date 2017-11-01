import Jama.*; 

public class FNNapp 
{
	public static void main(String[] args) 
	{
       
        
		double[][] input = {{0.5,0.1,0.0}}; // the 0.0m value always has to be added in order for the bias calculated by the neuralnetwork.java
			
		Matrix inputs = new Matrix(input);
		
        neuralNetwork test = new neuralNetwork(2,5,1,inputs);
        
    }
	
	
	
}
