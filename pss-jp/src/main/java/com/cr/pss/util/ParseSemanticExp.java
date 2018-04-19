/**
 * 
 */
package com.cnipr.pss.util;

/**
 * @author 戴国栋
 *
 * create 2010-1-11
 * company 知识产权出版社
 *@version 1.0 
 */
public class ParseSemanticExp {
	
	/**
	 * 分析智能检索表达式,将分析完成的表达式放到数组中,
	 * @param strSource
	 * @return 数组,元素[0]=智能检索和普通检索的关系,元素[0]=智能检索和普通检索的关系,元素[1]=智能检索提取的内容,元素[2]普通检索的表达式
	 * 
	 */
	public static String[] getSemanticExp(String strSource){
		String[] searchParam=null;
		/**
		 * 增加表达式中如果有NOT运算符
		 */
		int notFlag=1;
		strSource=strSource.toLowerCase();
		if(strSource.indexOf("ss=")>-1){
			strSource=strSource.replaceAll("ss=", "智能检索=");
		}
		String regStr="";
		
		String leftSymbol="";
		
		String rightSymbol="";
		
		String semanticCond="";
		
		String relation="";
		
		String commonCond="";
		
		String leftStr="";
		
		String rightStr="";
		
		String leftFlag="";
		
		String[] symbolArr=new String[]{"and"," or "," not "," xor "," adj "," equ/10 "," xor/10 "," pre/10 "};
		
		int semanticFlag=strSource.indexOf("智能检索");
		
		if(semanticFlag>-1){
			
			if(semanticFlag!=0){
				leftStr=strSource.substring(0,strSource.indexOf("智能检索"));
				if(removeSymbole(leftStr).equals("")){
					semanticFlag=0;
					leftFlag=leftStr;
				}
				for(String leftTempsymbol:symbolArr){
					if(leftStr.lastIndexOf(leftTempsymbol)>-1){
						leftStr=strSource.substring(0,leftStr.lastIndexOf(leftTempsymbol));
						leftSymbol=leftTempsymbol;
						break;
					}
				}
			}
			regStr=strSource.substring(strSource.indexOf("智能检索=")+5);
			if(regStr.indexOf("=")>-1){
				regStr=regStr.substring(0,regStr.indexOf("="));
				for(String symtemp:symbolArr){
					if(regStr.indexOf(symtemp)>-1){
						rightSymbol=symtemp;
						semanticCond=regStr.substring(0,regStr.indexOf(symtemp));
						regStr=regStr.substring(regStr.indexOf(symtemp)+symtemp.length(),regStr.length());
						rightStr=regStr+strSource.substring(strSource.indexOf(regStr)+regStr.length(),strSource.length());
						break;
					}
				}
			}else{
				semanticCond=regStr;
			}if(semanticFlag==0){
				notFlag=0;
				relation=rightSymbol;
				commonCond=leftFlag+rightStr;
			}else{
				relation=leftSymbol;
				commonCond=leftStr+rightSymbol+rightStr;
			}
			
			searchParam=new String[]{relation,removeSymbole(semanticCond),checkBracket(commonCond),Integer.toString(notFlag)};
		}else{
			commonCond=strSource;
			
			searchParam=new String[]{relation,removeSymbole(semanticCond),checkBracket(commonCond),Integer.toString(notFlag)};
		}
		return searchParam;
		
	}
	public static void main(String[] args){
		System.out.println(getSemanticExp("申请人=% not ss=计算机 and 公告号=%" ));
	}
	/**
	 * 去除左右括号
	 * @param source
	 * @return
	 */
	public static String removeSymbole(String source){
		return source.replaceAll("\\(|\\)|（|）", "");
	}
	/**
	 * 检查括号是否匹配
	 * @param sourceStr
	 * @return
	 */
	private static String checkBracket(String sourceStr){
	
		boolean  balance=true;
		char[] ch=sourceStr.toCharArray();
        int i=0;
        int chi=0;
        int chj=0;
        
        while(i<ch.length){
            if(ch[i]=='('){
                chj++;
            }
            if(ch[i]==')'){
                chj--;
            }
          i++;     
        }
        i=0;
        
        while(i<ch.length && balance==true){
                     
            while(i<ch.length){
             if(ch[i]=='(' && ch[i]!=')'){
                    chi++;
                }else if(ch[i]==')'){
                    break;
                }     
                i++;
            }
            for( ;i<ch.length;i++){
                if(ch[i]==')' && ch[i]!='('){
                    chi--;
                }else if(ch[i]=='('){
                    break;
                }
            }
            if(chi!=0){
            	balance=false;
            }
            if(chi>0&&chj!=0){
                sourceStr+=")";
            }
            if(chi<0&&chj!=0){
            	sourceStr="("+sourceStr;
            }
       }
        return sourceStr;
	}

}
