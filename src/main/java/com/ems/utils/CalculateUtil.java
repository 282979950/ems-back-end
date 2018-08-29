package com.ems.utils;

import com.ems.common.CalClass;
import com.ems.entity.SysDictionary;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author Administrator on 2018/8/29.
 */
public class CalculateUtil {
//    private static BigDecimal sum = new BigDecimal(0);
    public static List<CalClass> callist = new ArrayList<CalClass>();

    // 气量转价格
    public static BigDecimal gasToPayment(BigDecimal gas) {
        BigDecimal sum = new BigDecimal(0);
        boolean flag = false;
       for (int i = 0; i < callist.size(); i++) {
            CalClass cal = callist.get(i);
            if( (gas.compareTo(cal.getStart())>=0 && (null == cal.getEnd()) || ( gas.compareTo(cal.getStart())>=0 && gas.compareTo(cal.getEnd())<=0)) ) {
                sum = sum.add(cal.getMoney().multiply( gas.subtract(cal.getStart()).add(BigDecimal.ONE)));
                gas = cal.getStart().subtract(BigDecimal.ONE);
                flag = true;
                break;
            }
        }
        if(gas.compareTo(BigDecimal.ZERO)>0  && flag) {
            return sum .add(gasToPayment(gas));
        }
        return sum;
    }

    public static List<CalClass> transfer(List<SysDictionary> dicList) {
        List<CalClass> list = new ArrayList<CalClass>();
       for(SysDictionary dic : dicList){
        ;   String quit = dic.getDictKey();
            int index = quit.indexOf("-");
            if (index != -1) {
                BigDecimal start =new BigDecimal(quit.substring(0, index));
                BigDecimal end = null;
                if(index+1<quit.length())
                    end = new BigDecimal(quit.substring(index + 1, quit.length()));
                BigDecimal price = new BigDecimal(dic.getDictValue());
                CalClass cal = new CalClass(start, end, price);
                list.add(cal);
            }
        }
        return list;
    }
}
