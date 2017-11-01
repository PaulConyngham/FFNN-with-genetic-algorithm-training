import Jama.*; 
import java.util.ArrayList;
import java.lang.Math.*;
//import java.util.ArrayList;

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
	
	//local variables for this class
	private int inputLayerSizeLocal = 0;
	private int hiddenLayerSizeLocal = 0;
	private int outputLayerSizeLocal = 0;
	
	//loca matrix arrays for this class
	private Matrix connectionWeightsOne = new Matrix(0, 0);
	private Matrix connectionWeightsTwo = new Matrix(0, 0);
	
	//constant value for this class
	private static double FNNBias = 1.0;
	
	//constant value for this NN's fitness
	private double FNNFitness = 0.0;
	
	//Arraylist to hold the neuralnetworks genes
	private ArrayList<Double> chromosome = new ArrayList<Double>();

	public neuralNetwork(int inputLayerSize, int hiddenLayerSize, int outputLayerSize, Matrix inputs) 
	//public void neuralNetwork() 
	{
		
		//set class's local variables
		
		//setInputLayerSizeLocal(inputLayerSize); //old
		inputLayerSizeLocal = inputLayerSize;
		
		
		//setHiddenLayerSizeLocal(hiddenLayerSize); //old
		hiddenLayerSizeLocal = hiddenLayerSize;
	
		//setOutputLayerSizeLocal(outputLayerSize); //old
		outputLayerSizeLocal = outputLayerSize;
		
		
		//set inputs bias neuron
		
		inputs.set(0, inputLayerSize,FNNBias);	
		//inputs.print(inputLayerSize,hiddenLayerSize);
		
		//the +1's here are because of the hidden bias neuron
		connectionWeightsOne = Matrix.random(inputLayerSize+1,hiddenLayerSize+1);
		//connectionWeightsOne.print(inputLayerSize+1,hiddenLayerSize+1);
		
		//the +1's here are because of the hidden bias neuron
		connectionWeightsTwo = Matrix.random(hiddenLayerSize+1,outputLayerSize);
		
		encodeChromosome(connectionWeightsOne,connectionWeightsTwo);
		
		//connectionWeightsOne.print(inputLayerSize,hiddenLayerSize); //for testing...
		//Matrix Atwo = Matrix.random(hiddenLayerSize+1,1);
		
		//double[][] AtwoArray = new double[1][hiddenLayerSize+1];
		//Matrix Atwo = new Matrix(AtwoArray);
		//Atwo.print(hiddenLayerSize,1);
		Matrix Atwo = forward(inputs,connectionWeightsOne);
		
		int AtwoRowSize = Atwo.getRowDimension();
		int AtwoColumnSize = Atwo.getColumnDimension();		
		
		//System.out.printf("the row size of Atwo is  %d and the column size of Atwo is  %d", AtwoRowSize,AtwoColumnSize );
		
		//Atwo.print(hiddenLayerSize,1);
		
		
		//set Atwo bias neuron... essentially cheating in another bias neuron into the first activation layer
		Atwo.set(0, hiddenLayerSize,FNNBias);	
		//Atwo.print(hiddenLayerSize,1);
		Matrix Yhat = forward(Atwo,connectionWeightsTwo);
		//Yhat.print(1,1);
		//Ztwo.print(inputLayerSize+1,hiddenLayerSize); //old
		
		
		
		
		

    }
	
	public Matrix updateNeuralNetwork(Matrix inputs)
	{ 
		//inputs.set(0, inputLayerSizeLocal,FNNBias);	
		Matrix Atwo = forward(inputs,connectionWeightsOne);
		Matrix Yhat = forward(Atwo,connectionWeightsTwo);
		//Yhat.print(1,1);
		
		
		return Yhat;
	}


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
				
				//System.out.printf("%f ", tempVar );
			}
			//System.out.printf(" %n");
		}
		
        //System.out.println(" Activation computation complete"); 		
		
        //matrixA.print(AtwoRowSize+1,AtwoColumnSize+1);
		
		return matrixA;
	}
	
	
	/*
	public void setInputLayerSizeLocal(int inputLayerSize)
	{
		inputLayerSizeLocal = inputLayerSize;
	}
	
	public void setHiddenLayerSizeLocal(int hiddenLayerSize)
	{
		hiddenLayerSizeLocal = hiddenLayerSize;
	}
	
	public void setOutputLayerSizeLocal(int outputLayerSize)
	{
		outputLayerSizeLocal = outputLayerSize;
	}
	*/
	
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
		if (weightsmaxtrixIdentifier == 0)
		{
			connectionWeightsOne = tempConnectionWeights;
		}
		if (weightsmaxtrixIdentifier == 1)
		{
			connectionWeightsTwo = tempConnectionWeights;
		}
		
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
	
	//this needs completion
	public void convertChromosomeToWeightMatrix(ArrayList<Double> localChromosome)
	{
		int chromosomeSize = localChromosome.size();
		int chromosomeCounter = 0;
		
		
		Matrix connectionWeightsOneLocal = getWeights(0);
		int connectionWeightsOneLocalRowDim = connectionWeightsOneLocal.getRowDimension()+1;
		int connectionWeightsOneLocalColDim = connectionWeightsOneLocal.getColumnDimension()+1;
		
		for(int i = 0; i < connectionWeightsOneLocalRowDim; i++)
		{
			for(int j = 0; j < connectionWeightsOneLocalColDim; j++)
			{
				connectionWeightsOneLocal.set(i,j,localChromosome.get(chromosomeCounter));
				chromosomeCounter++;
				System.out.printf(" %f ", localChromosome.get(chromosomeCounter));
			}
			System.out.printf(" %n");
		}
		//set weight matrix 1
		setWeights(connectionWeightsOneLocal,0);
		
		
		Matrix connectionWeightsTwoLocal = getWeights(1);
		int connectionWeightsTwoLocalRowDim = connectionWeightsTwoLocal.getRowDimension()+1;
		int connectionWeightsTwoLocalColDim = connectionWeightsTwoLocal.getColumnDimension(); // no additional bias neuron required (yet)

		for(int x = 0; x < connectionWeightsTwoLocalRowDim; x++)
		{
			for(int y = 0; y < connectionWeightsTwoLocalColDim; y++)
			{
				connectionWeightsTwoLocal.set(x,y,localChromosome.get(chromosomeCounter));
				chromosomeCounter++;
				System.out.printf(" %f ", localChromosome.get(chromosomeCounter));
			}
			System.out.printf(" %n");
		}
		//set weight matrix 2
		setWeights(connectionWeightsTwoLocal,1);
		
	}
	
	//this will need to modified if additional hidden layers are required.
	public void encodeChromosome(Matrix weightsMatrixOne, Matrix weightsMatrixTwo)
	{
		int weightsMatrixOneRowDimension = connectionWeightsOne.getRowDimension();
		int weightsMatrixOneColumnDimension = connectionWeightsOne.getColumnDimension();
		int weightsMatrixOneTotalElements = 0;
		
		int weightsMatrixTwoRowDimension = connectionWeightsTwo.getRowDimension();
		int weightsMatrixTwoColumnDimension = connectionWeightsTwo.getColumnDimension();
		int weightsMatrixTwoTotalElements = 0;
		
		
		
		 double[] tempArray1 = new double[weightsMatrixOneRowDimension*weightsMatrixOneColumnDimension];
		 //System.out.printf("tempArray1 holds %d items %n", tempArray1.length);
		 double[] tempArray2 = new double[weightsMatrixTwoRowDimension*weightsMatrixTwoColumnDimension];
		 //System.out.printf("tempArray2 holds %d items %n", tempArray2.length);
		 
		 tempArray1 = connectionWeightsOne.getRowPackedCopy();
		 tempArray2 = connectionWeightsTwo.getRowPackedCopy();
		 
		 //loop through tempArray1's variables and store them in the chromosome for this neuralnetwork.
		 for(int k = 0; k < tempArray1.length; k++)
		 {
			 chromosome.add(tempArray1[k]);
			 weightsMatrixOneTotalElements++;
		 }
		 
		 for(int j = 0; j < tempArray2.length; j++)
		 {
			 chromosome.add(tempArray2[j]);
			 weightsMatrixTwoTotalElements++;
		 }
		 
		 
		 //encoding the size of the original matrixes into the chromosome...
		 chromosome.add(((double) weightsMatrixOneTotalElements));
		 chromosome.add(((double) weightsMatrixTwoTotalElements));
		 
		 //System.out.printf("chromosome holds %d items %n", chromosome.size());
		 
		 
		 //simple test to see that chromosome does not hold garbage
		 /*
		 for(int z = 0; z < chromosome.size(); z++)
		 {
			 System.out.printf("chromosome item %d is %f %n", z, chromosome.get(z));
		 }
		 */
	}
	

	
	public ArrayList<Double> getChromosome()
	{
		return chromosome;
	}
	
	public void setChromosome(ArrayList<Double> localChromosome)
	{
		chromosome = localChromosome;
	}
	
	
	public void setFNNFitness(double fitness)
	{
		FNNFitness = fitness;	
	}
	
	public double getFNNFitness()
	{
		return FNNFitness;
	}	
	
	public int getInputLayerSizeLocal()
	{
		return inputLayerSizeLocal;
	}	
	
	public int getHiddenLayerSizeLocal()
	{
		return hiddenLayerSizeLocal;
	}	

	public int getOutputLayerSizeLocal()
	{
		return outputLayerSizeLocal;
	}		
	
}
