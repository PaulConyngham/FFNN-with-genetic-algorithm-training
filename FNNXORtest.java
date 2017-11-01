import java.util.ArrayList;

import Jama.Matrix;


public class FNNXORtest
{	
	public void FNNXORtest(int sampleSize) 
	{
		//int sampleSize = 50000;
	
		// input Initialization array
		double[][] inputInitializationArray = {{0.5,0.5,0.0}}; // to be used for all added items	
		Matrix inputInitializationMatrix = new Matrix(inputInitializationArray);

	
		ArrayList<neuralNetwork> neuralNetworkCollection = new ArrayList<neuralNetwork>();
           
		//add 50 neural network objects to be evaluated
		for(int i=0; i < sampleSize ; i++)
		{
			neuralNetwork tempNeuralNetworkObject = new neuralNetwork(2,5,1,inputInitializationMatrix);
			neuralNetworkCollection.add(tempNeuralNetworkObject);
    	
			//System.out.printf("element %d added", i);
		}
		
		FNNFitnesFunc FNNFitnesFuncObject = new FNNFitnesFunc(10);
		
		ArrayList<neuralNetwork> evaluatedNeuralNetworkCollection = new ArrayList<neuralNetwork>();
    
		evaluatedNeuralNetworkCollection = FNNFitnesFuncObject.XORErrorEval(neuralNetworkCollection);
		
		System.out.printf("evaluatedNeuralNetworkCollection size is %d %n",evaluatedNeuralNetworkCollection.size());
		neuralNetwork tempNeuralNetworkObject2 = evaluatedNeuralNetworkCollection.get(1);
		System.out.printf("tempNeuralNetworkObject2 score is %f %n",tempNeuralNetworkObject2.getFNNFitness());
    
		System.out.printf("evaluation complete %n");
		
		
		FNNevolution FNNevolutionObject = new FNNevolution(-0.5);
		ArrayList<neuralNetwork> nextGenerationNeuralNetworkCollection = new ArrayList<neuralNetwork>();
		nextGenerationNeuralNetworkCollection = FNNevolutionObject.FNNevolve(evaluatedNeuralNetworkCollection,10,1);
		
		//evaluatedNeuralNetworkCollection,10,1
		
	}
}
