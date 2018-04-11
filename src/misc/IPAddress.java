package misc;

public class IPAddress {

	public static void main(String[] args) {
		printIPAddressCombinations("12341");
	}
	
	/**
	 * 1234 -> 1.2.3.4
	 * 12345 -> 1.2.3.45  -> 
	 * 1.2.34.5
	 * 1.23.4.5
	 * 12.3.4.5
	 * 112112 --
	 * 1.2.31.12 --> position of third dot >3 digit < 6 ==> 3 comb
	 * 1.2.31.12
	 * 1.2.311.2 --> position of 2nd dot > 2
	 * 1.23.11.2
	 * 1.231.1.2
	 * 12.31.1.2
	 * 123.1.1.2
	 * 
	 * these dot positions are not same as char positions
	 * first dot pos 1 , 2 --> i
	 * second dot post --> i+1, i+2 -->j
	 * third dot position --> j+1, j+2
	 * 
	 * 
	 * 	 * 
	 * 
	 */
	private static void printIPAddressCombinations(String str){
		char [] ipAdd = str.toCharArray();
		int length = str.length();
		
		if(length == 4){
			StringBuilder sb = new StringBuilder();
			boolean first = true;
			for(char c : ipAdd){
				if(!first){
					sb.append(".");
				}
				sb.append(c);
				first = false;
			}
			System.out.println(sb.toString());
		}
		else if (length == 8 ){
			StringBuilder sb = new StringBuilder();
			int count = 0; 
			boolean first = true;
			for(char c : ipAdd){
				if(count%2 == 0 && !first){
					sb.append(".");
				}
				sb.append(c);
				count++;
				first = false;
			}
			System.out.println(sb.toString());
		}
		else {
				char [] ipChars = new char[11];
				
//				int count = 0;
//				while(true){
					//copy char till count reaches value of i					
					for(int i=1;i<=2;i++){
						for (int j=i+1;j<=i+2;j++){
							for(int k=j+1;k<=j+2;k++){
								System.out.println(insertDots(str,i,j,k));
							}
						}
						
					}
					
					
//				}
			}
		}

	private static String insertDots(String str, int i, int j, int k) {
		StringBuilder sb = new StringBuilder();
		for(int index = 0; index<str.length();){
			if(index != i && index != j && index !=k)
				sb.append(str.charAt(index++));
			else {
				sb.append(".");
				sb.append(str.charAt(index++));
			}
		}
		return sb.toString();
		
	}
}
