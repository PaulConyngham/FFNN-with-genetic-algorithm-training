
import java.util.ArrayList;

import Jama.*; 

public class FNNFitnesFunc

{
	private int fitnessRate = 0;
	
	public FNNFitnesFunc(int newFitnessRate)
	{
		setFitnessRate(newFitnessRate);
	}
	
	public ArrayList<neuralNetwork> XORErrorEval(ArrayList<neuralNetwork>  neuralNetworkCollection)
	{
		ArrayList<neuralNetwork> sortedNeuralNetworkCollection = new ArrayList<neuralNetwork>();
		ArrayList<neuralNetwork> evaluatedNeuralNetworkCollection = new ArrayList<neuralNetwork>();
		
		
		
		ArrayList<Double> neuralNetworkScoreCollection = new ArrayList<Double>();
		int sampleSize = neuralNetworkCollection.size();
		
        // XOR input values
		double[][] input0 = {{0.0,0.0,0.0}}; // output should be 0
		Matrix input0M = new Matrix(input0);
		double[][] input1 = {{0.0,1.0,0.0}}; // output should be 1
		Matrix input1M = new Matrix(input1);
		double[][] input2 = {{1.0,0.0,0.0}}; // output should be 1
		Matrix input2M = new Matrix(input2);
		double[][] input3 = {{1.0,1.0,0.0}}; // output should be 0
		Matrix input3M = new Matrix(input3);
		
		
        //test neuralNetworkCollection's item k for its score on the  different inputs... then mutate
        for(int j=0; j < sampleSize ; j++)
        {
        		double totalError = 0.0;
        		double currentError = 0.0;
        		double currentYhat = 0.0;
        		
        		//neuralNetwork tempNeuralNetworkObject =  new neuralNetwork();
        		neuralNetwork tempNeuralNetworkObject =  neuralNetworkCollection.get(j);
        		Matrix tempYhat = tempNeuralNetworkObject.updateNeuralNetwork(input0M);
        		currentYhat = tempYhat.get(0,0);
        		currentError = 0.0 - currentYhat;
        		totalError = totalError + currentError;
        		        		
        		tempYhat = tempNeuralNetworkObject.updateNeuralNetwork(input1M);
        		currentYhat = tempYhat.get(0,0);
        		currentError = currentYhat - 1.0;        		
        		totalError = totalError + currentError;
        		
        		tempYhat = tempNeuralNetworkObject.updateNeuralNetwork(input2M);
        		currentYhat = tempYhat.get(0,0);
        		currentError = currentYhat - 1.0;        		
        		totalError = totalError + currentError;     
        		
        		tempYhat =  tempNeuralNetworkObject.updateNeuralNetwork(input3M);
        		currentYhat = tempYhat.get(0,0);
        		currentError = 0.0 - currentYhat;
        		totalError = totalError + currentError;
        		
        		neuralNetworkScoreCollection.add(totalError);
        		tempNeuralNetworkObject.setFNNFitness(totalError);
        		
        		neuralNetworkCollection.set(j,tempNeuralNetworkObject);
        		//System.out.printf("error for object %d ...%f %n", j, totalError);
        		
        	
        }
        
        //QuickSort quicksortApp = new QuickSort();
        sortedNeuralNetworkCollection = quicksortFNN(neuralNetworkCollection);
        
        
        /*
        for(int z=0; z < ((sortedNeuralNetworkCollection.size())) ; z++)
        {
        	neuralNetwork tempNeuralNetworkObject = sortedNeuralNetworkCollection.get(z);
        	double tempHighestScore = tempNeuralNetworkObject.getFNNFitness();
        	
        	//if(tempHighestScore > -1.96)
        	//{
        		System.out.printf("item %d is %f %n",z, tempHighestScore);
        		
        		//neuralNetwork tempNeuralNetworkObject2 =  neuralNetworkCollection.get(z);
        		
        	//}
        }
        */
        
        for(int k=sortedNeuralNetworkCollection.size(); k > (int) (((sortedNeuralNetworkCollection.size()))-(((sortedNeuralNetworkCollection.size())*fitnessRate)/100)) ; k--)
        {
        	neuralNetwork tempNeuralNetworkObject = sortedNeuralNetworkCollection.get(k-1);
        	double tempHighestScore = tempNeuralNetworkObject.getFNNFitness();
        	
        	System.out.printf("item %d is %f %n",k, tempHighestScore);
        	evaluatedNeuralNetworkCollection.add(tempNeuralNetworkObject);
        }
		
        System.out.printf("the 10 percent begin at... %d  %n",(int) (((sortedNeuralNetworkCollection.size()))-(((sortedNeuralNetworkCollection.size())*fitnessRate)/100)));
        
		return evaluatedNeuralNetworkCollection;
	}
	
	
	private ArrayList<neuralNetwork> quicksortFNN(ArrayList<neuralNetwork> neuralNetworkCollection){
		
		if(neuralNetworkCollection.size() <= 1){
			return neuralNetworkCollection;
		}
		
		int middle = (int) Math.ceil((double)neuralNetworkCollection.size() / 2);
		
		neuralNetwork tempNeuralNetworkObject = neuralNetworkCollection.get(middle);
		
		
		double pivot = tempNeuralNetworkObject.getFNNFitness();
 
		ArrayList<neuralNetwork> less = new ArrayList<neuralNetwork>();
		//ArrayList<neuralNetwork> lessNN = new ArrayList<neuralNetwork>();
		
		ArrayList<neuralNetwork> greater = new ArrayList<neuralNetwork>();
		//ArrayList<neuralNetwork> greaterNN = new ArrayList<neuralNetwork>();
		
		for (int i = 0; i < neuralNetworkCollection.size(); i++) 
		{
			neuralNetwork tempNeuralNetworkObject2 = neuralNetworkCollection.get(i);
			
			if(tempNeuralNetworkObject2.getFNNFitness() <= pivot){
				if(i == middle){
					continue;
				}
				less.add(neuralNetworkCollection.get(i));
				//lessNN.add(neuralNetworkCollection.get(i));
			}
			else{
				greater.add(neuralNetworkCollection.get(i));
				//greaterNN.add(neuralNetworkCollection.get(i));
			}
		}
		
		return concatenate(quicksortFNN(less), tempNeuralNetworkObject, quicksortFNN(greater));
	}
	
	/**
	 * Join the less array, pivot integer, and greater array
	 * to single array.
	 * @param less integer ArrayList with values less than pivot.
	 * @param pivot the pivot integer.
	 * @param greater integer ArrayList with values greater than pivot.
	 * @return the integer ArrayList after join.
	 */
	private ArrayList<neuralNetwork> concatenate(ArrayList<neuralNetwork> less,neuralNetwork pivot, ArrayList<neuralNetwork> greater)
	{
		
		ArrayList<neuralNetwork> list = new ArrayList<neuralNetwork>();
		//ArrayList<neuralNetwork> NNlist = new ArrayList<neuralNetwork>() ;
		
		for (int i = 0; i < less.size(); i++) {
			list.add(less.get(i));
			//NNlist.add(lessNN.get(i));
		}
		
		list.add(pivot);
		
		for (int i = 0; i < greater.size(); i++) {
			list.add(greater.get(i));
			//NNlist.add(greaterNN.get(i));
		}
		
		return list;
	}
	
	private void setFitnessRate(int newFitnessRate)
	{
		fitnessRate = newFitnessRate;
	}
}
