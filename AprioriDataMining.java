import java.io.*;
import java.util.*;
public class AprioriDataMining
{
	Vector<String> candidates=new Vector<String>();
	List<String> itemSet = new ArrayList<String>();
	List<String> finalFrequentItemSet = new ArrayList<>();
	HashMap<String,Integer> frequentItems = new HashMap<String, Integer>();
	String newLine = System.getProperty("line.separator");
	int itemsCount,countItemOccurrence=0,displayFrequentItemSetNumber=2,displayTransactionNumber=1;
	
	/* PARAMETERS
	 * candidates :- Vector which stores all the combinations
	 * finalFrequentItemSet :- List that stores of all the frequent items set
	 * frequentItems :- stores list of frequent items set along with their support
	 * itemsCount :- no of items present in database
	 * noOfTransactions :- stores total no of transactions
	 * itemSet :- List that stores all the unique items present in database
	 * transactions :- List that stores all transactions of database
	 * minimumSupport :- stores minimum support for transactional database
	 * minimumConfidence :- stores minimum confidence (double value)
	 */
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int noOfTransactions,minimumSupport;
		double minimumConfidence;
		String sampleFile = args[0];
		List<String> transactions = new ArrayList<String>();
		
		String newLine = System.getProperty("line.separator");
		
		System.out.println(newLine+"'APRIORI ALGORITHM'"); 
		System.out.print("Enter the Minimum Support = ");
		minimumSupport = Integer.parseInt(br.readLine()); 
		System.out.print("Enter the Minimum Confidence (in %) = ");	
		minimumConfidence = Double.parseDouble(br.readLine());
		minimumConfidence = minimumConfidence/100;
		
		File file = new File(sampleFile);
		Scanner sc = new Scanner(file);
		
		while (sc.hasNextLine())
		{
			String str = sc.nextLine();
			transactions.add(str);
		}
	
		noOfTransactions = transactions.size();
		
		AprioriDataMining a = new AprioriDataMining();
		a.display(noOfTransactions, transactions, minimumSupport, minimumConfidence);
	}
	
	/* function display(noOfTransactions, transactions, minimumSupport, minimumConfidence);
	 * parses unique items from database and stores in List itemSet
	 */
	public void display(int noOfTransactions, List<String> transactions, int minimumSupport, double minimumConfidence)
	{
		for(int i = 0; i<noOfTransactions;i++)
		{
			String str = transactions.get(i);
			String[] words = str.split(" ");
			int count = words.length;
			for(int j=0;j<count;j++)
			{
				if(i==0)
				{
					itemSet.add(words[j]);
				}
				else
				{ 
					if(!(itemSet.contains(words[j])))
					{
						itemSet.add(words[j]);
					}
				}
			}
		}
		
		itemsCount = itemSet.size(); 
		System.out.println(newLine+"No of Items = "+itemsCount); 
		System.out.println("No of Transactions = "+noOfTransactions); 
		System.out.println("Minimum Support = "+minimumSupport); 
		System.out.println("Minimum Confidence = "+minimumConfidence+newLine);
		
		System.out.println("'Items present in the Database'");
		for(String i : itemSet)
		{
			System.out.println("------> "+i);
		}
		
		System.out.println(newLine+"TRANSACTION ITEMSET");
		for(String i : transactions)
		{
			System.out.println("Transaction "+displayTransactionNumber+" = "+i);
			displayTransactionNumber++;
		}
		firstFrequentItemSet(noOfTransactions,transactions,minimumSupport,minimumConfidence);
	}
	
	/* firstFrequentItemSet(noOfTransactions,transactions,minimumSupport,minimumConfidence);
	 * it calculates Frequent Item Set 1 and stores in HashMap frequentItems with items
	 * and corresponding support values if support is greater than minimum support
	 */
	public void firstFrequentItemSet(int noOfTransactions,List<String> transactions,int minimumSupport, double minimumConfidence)
	{
		System.out.println();
		/* calculates the occurrence of individual items in the transactional database,
		* each item is then checked in all transactions and then the occurrence is then
		* compared with minimum support and if it is greater, then added in HashMap "frequentItems"
		*/
		for(int items=0;items<itemSet.size();items++)
		{
			countItemOccurrence=0;
			String itemStr = itemSet.get(items);
			for(int t=0;t<noOfTransactions;t++)
			{
				String transactionStr = transactions.get(t);
				if(transactionStr.contains(itemStr))
				{
					countItemOccurrence++;
				}
			}
			if(countItemOccurrence >= minimumSupport)
			{
				System.out.println("Frequent Itemset 1 => Item = '"+itemStr+"' => support = "+countItemOccurrence);
				finalFrequentItemSet.add(itemStr);
				frequentItems.put(itemStr, countItemOccurrence);
			}
		}
		
		aprioriStart(noOfTransactions,transactions,minimumSupport,minimumConfidence);
	}
	
	public void aprioriStart(int noOfTransactions,List<String> transactions,int minimumSupport, double minimumConfidence)
	{
		int itemsetNumber=1;
		
		/* add all the items in candidates vector which would be required for generating combinations */
		for(int i=0;i<finalFrequentItemSet.size();i++)
		{
			String str = finalFrequentItemSet.get(i);
			candidates.add(str);
		} 
		
		do
		{
			itemsetNumber++;
			generateCombinations(itemsetNumber);
			checkFrequentItems(noOfTransactions,transactions,minimumSupport);
		}
		while(candidates.size()>1);
		
		System.out.println("Association Rules for Frequent Itemset"+newLine);
		
		generateAssociationRules(noOfTransactions, transactions, minimumConfidence);
	}
	
	/* generateCombinations(itemSetNumber)
	* input parameters :- itemsetNumber - iteration number i.e. combination of 2 or 3 or 4......
	*/
	private void generateCombinations(int itemsetNumber)
	{
		Vector<String> candidatesTemp = new Vector<String>();
		String s1, s2;
		StringTokenizer strToken1, strToken2;
		if(itemsetNumber==2)
		{
			for(int i=0; i<candidates.size(); i++)
			{
				strToken1 = new StringTokenizer(candidates.get(i));
				s1 = strToken1.nextToken();
				for(int j=i+1; j<candidates.size(); j++)
				{
					strToken2 = new StringTokenizer(candidates.elementAt(j));
					s2 = strToken2.nextToken();
					String addString = s1+" "+s2;
					candidatesTemp.add(addString);
				}
			}
		}
		else
		{
			for(int i=0; i<candidates.size(); i++)
			{
				for(int j=i+1; j<candidates.size(); j++)
				{
					s1 = new String();
					s2 = new String();
					
					strToken1 = new StringTokenizer(candidates.get(i));
					strToken2 = new StringTokenizer(candidates.get(j));
					
					for(int s=0; s<itemsetNumber-2; s++)
					{
						s1 = s1 + " " + strToken1.nextToken();
						s2 = s2 + " " + strToken2.nextToken();
					}
					
					if(s2.compareToIgnoreCase(s1)==0)
					{
						String addString = (s1 + " " + strToken1.nextToken() + " " + strToken2.nextToken()).trim();
						candidatesTemp.add(addString);
					}
				}
			}
		}
		candidates.clear();
		candidates = new Vector<String>(candidatesTemp);
		candidatesTemp.clear();
		System.out.println();
	}

	
	public void checkFrequentItems(int noOfTransactions,List<String> transactions, int minimumSupport)
	{
		List<String> combList = new ArrayList<String>();
		for(int i=0;i<candidates.size();i++)
		{
			String str = candidates.get(i);
			combList.add(str);
		}
		
		/* below mentioned for loop takes into account each item in list which is then split up and stored in words[],
		* each word is compared with each transaction and if all the words of item set is present in that particular
		* transaction, then itemSetOccurence is incremented. This runs till all the transactions are checked and thus
		* the support for that particular item set is calculated and if it is above minimum support, then it is stored
		* in frequentItems HashMap along with the support, also in finalFrequentItemSet list.
		*/

		for(int i=0;i<combList.size();i++)
		{
			String str = combList.get(i);
			String[] words = str.split(" ");
			int count = words.length;
			int flag = 0, itemSetOccurence=0;
			for(int t=0;t<noOfTransactions;t++)
			{
				String transac = transactions.get(t);
				for(int j=0;j<count;j++)
				{
					String wordStr = words[j];
					if(transac.contains(wordStr))
					{
						flag++;
					}
				}
				if(flag==count)
				{
					itemSetOccurence++;
				}
				flag=0;
			}
			if(itemSetOccurence>=minimumSupport)
			{
				System.out.println("Frequent Itemset "+displayFrequentItemSetNumber+" => Itemset = '"+ str+"' => support = "+itemSetOccurence);
				frequentItems.put(str, itemSetOccurence);
				finalFrequentItemSet.add(str);
			}
			itemSetOccurence=0;
		}
		displayFrequentItemSetNumber++;
	}
	
	public void generateAssociationRules(int noOfTransactions,List<String> transactions,double minimumConfidence)
	{
		double confidence,confidence1;
		
		/* below mentioned for loop takes each item set from finalFrequentItemSet List, 
		* checks it's support from frequentItems HashMap,
		* splits the item set and stores individual words into words[]. 
		* If wordCount is 2, then only 2 association rules can be generated (i.e. a->b, b->a). 
		* Then it checks support of first word and finds confidence and if it is above minimumConfidence,
		* then displays it. Same flow for wordCount>2. In case of wordCount>2, it generates combinations of rules.
		*/

		for(int i=0;i<finalFrequentItemSet.size();i++)
		{
			String itemSetStr = finalFrequentItemSet.get(i);
			double value = frequentItems.get(itemSetStr);
			String str = "",str1="";
			String[] words = itemSetStr.split(" ");
			int wordCountInString = words.length;
			if(wordCountInString==2) /* for FrequentItemSet = 2*/
			{
				double s = frequentItems.get(words[0]);
				confidence = value/s;
				if(confidence>=minimumConfidence)
				{
					System.out.println("'"+words[0]+" -> "+words[1]+"' = {Confidence = "+ confidence +" and Support = "+(int)value+"}");
				}
				double s1 = frequentItems.get(words[1]);
				confidence = value/s1;
				if(confidence>=minimumConfidence)
				{
					System.out.println("'"+words[1]+" -> "+words[0]+"' = {Confidence = "+ confidence+" and Support = "+(int)value+"}");
				}
			}
			else /* for FrequentItemSet > 2 */
			{
				for(int a=0;a<wordCountInString-1;a++)
				{
					if(a==0)
					{
						str = str+words[a];
					}
					else
					{
						str = str+" "+words[a];
					}
					for(int j=a+1;j<wordCountInString;j++)
					{
						{
							str1=str1+" "+words[j];
						}
					}
					double s = frequentItems.get(str);
					confidence = value/s;
					String st = str1.trim();
					double s1 = frequentItems.get(st);
					confidence1 = value/s1;
					if(confidence>=minimumConfidence)
					{
						System.out.println("'"+str+" -> "+str1+"' = {Confidence = "+confidence+" and Support = "+(int)value+"}");
					}
					if(confidence1>=minimumConfidence)
					{
						System.out.println("'"+st+" -> "+str+"' = {Confidence = "+confidence1+" and Support = "+(int)value+"}");
					}
					str1="";
				}
				str="";str1="";
			}
		}
	}
}
