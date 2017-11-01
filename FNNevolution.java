import java.util.ArrayList;
import java.util.Random;

import Jama.*; 

public class FNNevolution 
{

	//local variables for this class
	//private double evolutionErrorCutoffRate = 0.0;

	public FNNevolution(double evolutionError)
	{
		setMaxEvolutionError(evolutionError);
		//add constructor here
	}
	
	public ArrayList<neuralNetwork> FNNevolve(ArrayList<neuralNetwork> evaluatedNeuralNetworkCollection, int fitnessRate, int evolutionType)
	{
		ArrayList<neuralNetwork> nextNeuralNetworkGenerationCollection = new ArrayList<neuralNetwork>();
		nextNeuralNetworkGenerationCollection.addAll(evaluatedNeuralNetworkCollection);
		
		double[][] inputInitializationArray = {{0.5,0.5,0.0}};
		Matrix inputInitializationMatrix = new Matrix(inputInitializationArray);
		
		
		//possibly outdated way of getting the split locations
		
		/*
		//extract the amount of numbers stored in each weight matrix
		neuralNetwork tempNeuralNetworkObject3 = evaluatedNeuralNetworkCollection.get(0);
		int tempNeuralNetworkObject3InputLayerSize = tempNeuralNetworkObject3.getInputLayerSizeLocal();
		int tempNeuralNetworkObject3HiddenLayerSize = tempNeuralNetworkObject3.getHiddenLayerSizeLocal();
		int tempNeuralNetworkObject3OutputLayerSize = tempNeuralNetworkObject3.getOutputLayerSizeLocal();
		
		//the +1's here are because of the hidden bias neuron
		int weightsOneSize = (tempNeuralNetworkObject3InputLayerSize+1)*(tempNeuralNetworkObject3HiddenLayerSize+1);
		int weightsTwoSize = (tempNeuralNetworkObject3HiddenLayerSize+1)*tempNeuralNetworkObject3OutputLayerSize;
		
		int weightsOneSizeCrossOverNumber =  (int)(weightsOneSize/2);
		int weightsTwoSizeCrossOverNumber =  (int)(weightsTwoSize/2);
		
		
		*/
		
		
		/*
		 * This potentially has a flaw in that a child could be made that has the same properties twice- may need to  solve at a later stage 
		 *   
		 */
		
		if(evolutionType == 1)
		{
			double start = 0;
			double end = evaluatedNeuralNetworkCollection.size();
			
			for( int i = 0; i < (evaluatedNeuralNetworkCollection.size()); i++)
			{
				
				
				neuralNetwork tempNeuralNetworkObject1 =  evaluatedNeuralNetworkCollection.get(i);
				//mate "fitnessRate" times and add new objects to the arraylist nextNeuralNetworkGenerationCollection
				for( int j = 0; j < fitnessRate; j++)
				{
									
					double random = new Random().nextDouble();
					double randomDouble = start + (random * (end - start));
					int randomInt = (int) randomDouble;
					
					neuralNetwork tempNeuralNetworkObject2 =  evaluatedNeuralNetworkCollection.get(randomInt);
					ArrayList<Double> chromosome1 = tempNeuralNetworkObject1.getChromosome();
					ArrayList<Double> chromosome2 = tempNeuralNetworkObject2.getChromosome();
					
					ArrayList<Double> newChromosome = crossOverFNNevolutionMutation(chromosome1,chromosome2,fitnessRate);
					

					neuralNetwork tempNeuralNetworkObject = new neuralNetwork(2,5,1,inputInitializationMatrix);
					//tempNeuralNetworkObject.setChromosome(newChromosome);
					
					/*
					 * finish this code tomorrow
					 */
					tempNeuralNetworkObject.convertChromosomeToWeightMatrix(newChromosome);
				
					//write the code to encode the new chromosome into the child
					
				}
				//double random = new Random().nextDouble();
				
				
				
			}
		}
		
		return nextNeuralNetworkGenerationCollection;
		
	}
	
	public ArrayList<Double> crossOverFNNevolution(ArrayList<Double> chromosome1, ArrayList<Double> chromosome2)
	{
		ArrayList<Double> splicedChromosome = new ArrayList<Double>();
		
		int chromosome1Size = chromosome1.size()-2; //-2 because the original matrix data is encoded in the final two elements....
		int chromosome2Size = chromosome2.size()-2;
		
		int chromosomeSplitLocation = (int) ((crossOverPercentage*chromosome1Size)/100);
		System.out.printf("Chromosome split location is %d %n",chromosomeSplitLocation);
		
		
		if(chromosome1Size != chromosome2Size)
		{
			System.out.printf("Chromosome size mismatch %n");
		}
		
		for( int j = 0; j < chromosome2Size; j++)
		{
			splicedChromosome.add(chromosome2.get(j));
		}
		
		
		for(int i = 0; i< chromosomeSplitLocation; i++)
		{
			splicedChromosome.set(i,chromosome1.get(i));
		}
		
		
		return splicedChromosome;
	}
	
	public ArrayList<Double> crossOverFNNevolutionMutation(ArrayList<Double> chromosome1, ArrayList<Double> chromosome2, int mutateRate)
	{
		ArrayList<Double> splicedChromosome = new ArrayList<Double>();
		
		int chromosome1Size = chromosome1.size()-2; //-2 because the original matrix data is encoded in the final two elements....
		int chromosome2Size = chromosome2.size()-2;
		
		int chromosomeSplitLocation1 = (int) (chromosome1[(chromosome1.size()- 1)])/2; //the second to last element in this matrix is the size of the first weights matrix
		int chromosomeSplitLocation2 = (int) (chromosome1[chromosome1.size()])/2; //the last element in this matrix is the size of the second weights matrix
		
		// old code   -->  (int) ((crossOverPercentage*chromosome1Size)/100);
		
		
		System.out.printf("Chromosome split location is %d %n",chromosomeSplitLocation);
		
		
		if(chromosome1Size != chromosome2Size)
		{
			System.out.printf("Chromosome size mismatch %n");
		}
		
		for( int j = 0; j < chromosome2Size; j++)
		{
			splicedChromosome.add(chromosome2.get(j));
		}
		
		
		for(int i = 0; i < chromosomeSplitLocation1; i++)
		{
			splicedChromosome.set(i,chromosome1.get(i));
		}
		
		for(int k = chromosomeSplitLocation1*2; k < chromosomeSplitLocation2; k++)
		{
			splicedChromosome.set(k,chromosome1.get(k));
		}
		
		splicedChromosome.add(chromosome1.size()-1);
		splicedChromosome.add(chromosome1.size());
		
		
		
		//mutate the chromosome at a chance of mutateRate between the range of mutateRate <- the last part may need to be changed in the future
		for(int z = 0; z< (splicedChromosome.size() - 2); z++)
		{
			double random2 = new Random().nextDouble();
			
			if((random2 < mutateRate)&&(random2>0))
			{
				double currentDNA = splicedChromosome.get(z);
				double start = currentDNA - ((double) (mutateRate/100));
				double end = currentDNA + ((double) (mutateRate/100));
				double random = new Random().nextDouble();
				double newDNA = start + (random * (end - start));				
				splicedChromosome.set(z,newDNA);
			}	
			
			
			
			//splicedChromosome.set(i,);
		}
		
				
		return splicedChromosome;
	}
	
	public void setMaxEvolutionError(double evolutionError)
	{
		evolutionErrorCutoffRate = evolutionError;
	}
}
