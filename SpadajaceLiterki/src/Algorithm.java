import java.util.ArrayList;


public class Algorithm {

	public static char[][] cloneArray(char[][] src) {
	    int length = src.length;
	    char[][] target = new char[length][src[0].length];
	    for (int i = 0; i < length; i++) {
	        System.arraycopy(src[i], 0, target[i], 0, src[i].length);
	    }
	    return target;
	}
	
	public static char[][] removePair(char[][] T, int Y1, int X1, int Y2, int X2)
	{
		for(;Y1>0;Y1--)
		{
			T[Y1][X1]=T[Y1-1][X1];
		}
		T[0][X1]=' ';
		
		for(;Y2>0;Y2--)
		{
			T[Y2][X2]=T[Y2-1][X2];
		}
		T[0][X2]=' ';
		
		return T;
	}
	
	public static ArrayList<LetterTable> IDA_Star(LetterTable Start)
	{
		
		double CostLimit = Start.getLettersNumber();
		MinLetters = Start.getLettersNumber();
		Path.add(Start);
		
		//wywolujemy funkcje iteracyjnego przeszukiwania w glab ze zwiekszanym, co krok petli, limitem
		while(true)
		{
			S = DepthLimitedSearch(0, Path, CostLimit);
			Path = S.Path;
			CostLimit = S.Cost;
			if(Path != null) return Path;
			if(CostLimit == Double.MAX_VALUE) return null;
		}
	
	}
	
	private static Solution DepthLimitedSearch(double StartCost, ArrayList<LetterTable> Path, double CostLimit)
	{
		ExploredNodes++;//w tym miejscu mozna wypisywac na ekran ile wezlow odwiedzono
		
		//bierzemy ostani element ze sciezki i ustawiamy jako sprawdzany wezel
		LetterTable Node = Path.get(Path.size()-1);
		if(Node.getLettersNumber()<MinLetters) MinLetters = Node.getLettersNumber();
		//a tu mozna wypisac najlepsze jak dotad rozwiazanie
		double MinimumCost = StartCost + Node.getLettersNumber();
		
		//jesli koszt przekracza limit to zwracamy ten koszt
		if(MinimumCost > CostLimit)
		{
			
			return S.newSolution(MinimumCost, null);
		}
		//jesli znaleziono rozwiazanie
		if(Node.getLettersNumber()==0) return S.newSolution(CostLimit, Path);
		
		
		double NextCostLimit = Double.MAX_VALUE;
		//petla wywylujaca funkcje dla wszystkich nastepnych wezlow
		for(LetterTable LT: Node.getNextTables())
		{
			int Pairs = LT.calcPairs();
			
			if(Pairs == 0) continue; //jesli nie da sie znalesc pary w tym wezle to omijamy
			double NewStartCost = StartCost + (double)(1/Pairs); //liczymy koszt
			Path.add(LT);
			
			S = DepthLimitedSearch(NewStartCost, Path, CostLimit);
			
			if(S.Path!=null) Path = S.Path;  //jesli znalazlo rozwiazanie to przypisujemy do Path
			else Path.remove(Path.size()-1); //jesli nie to usuwamy ostatnio dodany wezel
			double NewCostLimit = S.Cost;
			
			if(S.Path != null) //zwracamy ew. znalezione rozwiazanie
			{
				return S.newSolution(NewCostLimit, Path);
			}
			NextCostLimit = NewCostLimit;
		}
		
		
		return S.newSolution(NextCostLimit, null);
	}
	public static int getExploredNodes() {
		return ExploredNodes;
	}
	public static int getMinLetters() {
		return MinLetters;
	}
	private static int MinLetters; //minimalna ilosc liter, ktora zostala na tablicy w trakcie wykonywania algorytmu
	private static int ExploredNodes = 0; //ilosc odwiedzonych wezlow
	private static Solution S = new Solution(); //klasa przechowujaca zmienne zwracane przez DLS
	private static ArrayList<LetterTable> Path = new ArrayList<LetterTable>();
}
