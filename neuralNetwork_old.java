import Jama.*; 
import java.lang.Math.*;

public class neuralNetwork
{

	/*
	 * This code is significantly bugged due to JAMA, the matrix multiplication library that I have been using.
	 * when the "matrix" objects are passed between methods, specifically between the "neuralNetwork" method
	 * and the "forward" method,  all of the the container DOUBLE values of the matrix are being rounded. I have no idea
	 * why this is occuring.
	 * 
	 * 
	 */
	
	
	private Matrix connectionWeightsOne = new Matrix(0, 0);
	private Matrix connectionWeightsTwo = new Matrix(0, 0);
	private static double FNNBias = 1.0;

	public neuralNetwork(int inputLayerSize, int hiddenLayerSize, int outputLayerSize, Matrix inputs) 
	//public void neuralNetwork() 
	{
		//set inputs bias neuron
		
		inputs.set(0, inputLayerSize,FNNBias);	
		inputs.print(inputLayerSize,hiddenLayerSize);
		
		connectionWeightsOne = Matrix.random(inputLayerSize+1,hiddenLayerSize+1);
		connectionWeightsOne.print(inputLayerSize+1,hiddenLayerSize+1);
		
		connectionWeightsTwo = Matrix.random(hiddenLayerSize+1,outputLayerSize);
		
		Matrix Atwo = forward(inputs,connectionWeightsOne);
		
		int AtwoRowSize = Atwo.getRowDimension();
		int AtwoColumnSize = Atwo.getColumnDimension();		
		
		System.out.printf("the row size of Atwo is  %d and the column size of Atwo is  %d", AtwoRowSize,AtwoColumnSize );
		
		Atwo.print(hiddenLayerSize,1);
		
		
		//set inputs bias neuron
		Atwo.set(0, hiddenLayerSize,FNNBias);	
		Atwo.print(hiddenLayerSize,1);
		Matrix Yhat = forward(Atwo,connectionWeightsTwo);
		Yhat.print(1,1);
		//Ztwo.print(inputLayerSize+1,hiddenLayerSize);
			

    }
	
	public double updateNeuralNetwork(Matrix inputs)


	public double sigmoid(double x)
	{
		return 1/(1+Math.exp(-1*x));
	}
	
	
	//calculates the activation matrix
	public Matrix forward(Matrix currentLayer, Matrix currentLayerWeights)
	{
		Matrix matrixZ = currentLayer.times(currentLayerWeights);
		Matrix matrixA = matrixZ.copy();
		
		//matrixA.print(inputLayerSize,hiddenLayerSize);
		
		
		int AtwoRowSize = matrixA.getRowDimension();
		int AtwoColumnSize = matrixA.getColumnDimension();
		
		for(int i=0; i < AtwoRowSize ; i++)
		{
			for(int j=0; j < AtwoColumnSize ; j++)
			{
				double tempVar = matrixA.get(i,j);
				
				
				tempVar = sigmoid(tempVar);
				matrixA.set(i,j,tempVar);
				
				System.out.printf("%f ", tempVar );
			}
			System.out.printf(" %n");
		}
		
        System.out.println(" Activation computation complete"); 		
		
        matrixA.print(AtwoRowSize+1,AtwoColumnSize+1);
		
		return matrixA;
	}
	
	public void setBias(double bias)
	{
		FNNBias = bias;
	}
	
	public double getBias()
	{
		return FNNBias;
	}
	
	public void setWeights(Matrix tempConnectionWeights, int weightsmaxtrixIdentifier)
	{
		//needs to be written
	}
	
	public Matrix getWeights(int weightsmaxtrixIdentifier)
	{
		if (weightsmaxtrixIdentifier == 0)
		{
			return connectionWeightsOne; //returns the first set of connection weights
		}
		if (weightsmaxtrixIdentifier == 1)
		{
			return connectionWeightsTwo; //returns the second set of connection weights
		}
		
		double[][] nullMatrixArray = new double[0][0];
		Matrix nullMatrix = new Matrix(nullMatrixArray);
		
		return nullMatrix; //else returns a null matrix'

	}
}
