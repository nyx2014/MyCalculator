package snc.lsr.util;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * Use postfix expression to calculate.
 * Created by nyx2015 on 2015/5/22.
 * All rights reserved.
 */
public class StackEx {
    java.util.Stack<StackEle> stack;
    Double res;
    public StackEx(){
        stack = new java.util.Stack<>();
        stack.push(new StackEle(0));
        res = 0.0;
    }
    public StackEx(String t){
        stack = new java.util.Stack<>();
        stack.push(new StackEle(0));
        res = Double.parseDouble(t);
    }

    private StackEle getThis(){return stack.lastElement();}
    private StackEle getThisPrev() {return stack.get(stack.size()-2);}
    private Boolean isOneZero(){return (stack.size()==1 && getThis().isZero());}

    public Boolean addNum(Integer i){
        try {
            if (getThis().isCalcNum())
                getThis().add(i);
            else if(getThis().type.isOpt())
                stack.push(new StackEle(i));
            else
                return false;
        }catch(EmptyStackException e){
            stack.push(new StackEle(i));
        }
        return true;
    }
    public Boolean addNum(type t){
        if(isOneZero()){
            stack.pop();stack.push(new StackEle(t));return true;
        }
        if(getThis().type!=type.OPT){
            stack.push(new StackEle(opt.TIMES));getThis().justCast=false;
        }
        stack.push(new StackEle(t));
        return true;
    }
    public String addRes(){
//        if(getThis().type.isOpt() && res!=0.0) {
//            stack.push(new StackEle(res));return res.toString();
//        }
//        return "";

        if(isOneZero())
            stack.pop();
        else if(res==0.0 || !getThis().type.isOpt())
            return "";
        if(res.intValue()==res) {
            stack.push(new StackEle(res.intValue()));
            return new Integer(res.intValue()).toString();
        } else {
            stack.push(new StackEle(res));
            return res.toString();
        }
    }
    public Boolean addOpt(opt opt){
//        if(stack.size()==1 && getThis().isZero() && opt== StackEx.opt.Rbracket)return false;
//        if(stack.size()>1 && getThis().isZero() && getThisPrev().type.isOpt()){stack.push(new StackEle(opt));return true;}
        if(isOneZero()){switch (opt){
            case PLUS:
            case MINUS:
            case TIMES:
            case DIVIDE:
            case factorial:break;
            case SQUARE:
            case Lbracket:
            case SIN:
            case COS:
            case TAN:
            case COT:stack.pop();break;
            case Rbracket:return false;
            //Maybe this?
//        }}else if(!getThis().isNum() && getThis().optr.isEleArith() && opt.isEleArith()) {
        }}else if(getThis().optr!=null && getThis().optr.isEleArith() && opt.isEleArith()) {
            getThis().optr = opt;
            return false;
        }else if(getThis().isNum() && (opt == StackEx.opt.Lbracket || opt.isTriFunc() || opt == StackEx.opt.SQUARE)) {
            stack.push(new StackEle(StackEx.opt.TIMES));
            stack.lastElement().justCast = false;
        }
        stack.push(new StackEle(opt));
        if(opt.isTriFunc())
            stack.push(new StackEle(StackEx.opt.Lbracket));
        return true;
    }
    public Boolean addDot(){
        if(getThis().type==type.DBL)
            return false;
        if(!getThis().addDot())
            stack.push(new StackEle(0.0));
        return true;
    }
    public Boolean removeEnding(){
//        if(stack.isEmpty() || stack.firstElement().isZero())
        if(stack.isEmpty() || isOneZero())
            return false;

        if(getThis().isCalcNum() && !getThis().isZero()){
            getThis().removeEnding();
            if (getThis().isZero())
                stack.pop();
            if(stack.isEmpty())
                stack.push(new StackEle(0));
            return true;
        }
        if(getThis().isZero() || (stack.size()>1 && !getThis().isCalcNum() && !getThisPrev().justCast && !getThisPrev().isNum()))//justCast here means autoAdd
            stack.pop();
        stack.pop();

        if(stack.isEmpty())
            stack.push(new StackEle(0));
        return true;
    }
    public void clear(){
        stack.clear();
        stack.push(new StackEle(0));
    }
    public String calc() throws Exception {
//        if(stack.isEmpty())
//            throw new Exception("stack is empty!");
//        if(getThis().type==type.OPT)
//            throw new Exception("expression contains error!");
//        if(stack.size()>9)//:P
//            throw new Exception("stack overflow");
        Stack<StackEle> postfix = new Stack<>();
        Stack<opt> optStack = new Stack<>();
        Stack<StackEle> calc = new Stack<>();
        for (StackEle ele:stack){
            switch (ele.type){
                case INT:
                case DBL:postfix.push(ele);break;
                case OPT:
                    if(optStack.isEmpty()){optStack.push(ele.optr);break;}
                    switch (ele.optr){
                        case PLUS : while (!optStack.isEmpty() && optStack.lastElement().ordinal()>= opt.PLUS.ordinal()  && optStack.lastElement()!= opt.Lbracket){postfix.push(new StackEle(optStack.pop()));}optStack.push(ele.optr);break;
                        case MINUS: while (!optStack.isEmpty() && optStack.lastElement().ordinal()>= opt.PLUS.ordinal()  && optStack.lastElement()!= opt.Lbracket){postfix.push(new StackEle(optStack.pop()));}optStack.push(ele.optr);break;
                        case TIMES: while (!optStack.isEmpty() && optStack.lastElement().ordinal()>= opt.TIMES.ordinal() && optStack.lastElement()!= opt.Lbracket){postfix.push(new StackEle(optStack.pop()));}optStack.push(ele.optr);break;
                        case DIVIDE:while (!optStack.isEmpty() && optStack.lastElement().ordinal()>= opt.TIMES.ordinal() && optStack.lastElement()!= opt.Lbracket){postfix.push(new StackEle(optStack.pop()));}optStack.push(ele.optr);break;
                        case SQUARE:while (!optStack.isEmpty() && optStack.lastElement().ordinal()>= opt.SQUARE.ordinal()&& optStack.lastElement()!= opt.Lbracket){postfix.push(new StackEle(optStack.pop()));}optStack.push(ele.optr);break;
                        case Rbracket:do{postfix.push(new StackEle(optStack.pop()));}while (!optStack.isEmpty()&&optStack.lastElement().equals(opt.Lbracket));break;
                        case factorial:optStack.push(opt.factorial);break;
                        default:switch (ele.optr){
                            case SIN:postfix.push(new StackEle(opt.SIN));break;
                            case COS:postfix.push(new StackEle(opt.COS));break;
                            case TAN:postfix.push(new StackEle(opt.TAN));break;
                            case COT:postfix.push(new StackEle(opt.COT));break;
                            case Lbracket:break;
                        }optStack.push(ele.optr);
                    }
                    break;
                case RND:postfix.push(new StackEle(Math.random()));break;
                case E  :postfix.push(new StackEle(2.718281828));break;
                case PI :postfix.push(new StackEle(3.141592654));break;
                default:break;
            }
        }
        while (!optStack.isEmpty())
            postfix.push(new StackEle(optStack.pop()));
        for (StackEle ele:postfix){
            switch (ele.type){
                case INT:
                case DBL:calc.push(ele);break;
                case OPT:
                    switch (ele.optr){
                        case PLUS:calc.push(new StackEle(calc.pop().toDouble()+calc.pop().toDouble()));break;
                        case MINUS:calc.push(new StackEle(-calc.pop().toDouble()+calc.pop().toDouble()));break;
                        case TIMES:calc.push(new StackEle(calc.pop().toDouble()*calc.pop().toDouble()));break;
                        case DIVIDE:calc.push(new StackEle(1/calc.pop().toDouble()*calc.pop().toDouble()));break;
                        case SQUARE:calc.push(new StackEle(Math.sqrt(calc.pop().toDouble())));break;
                        case SIN:calc.push(new StackEle(Math.sin(calc.pop().toDouble())));break;
                        case COS:calc.push(new StackEle(Math.cos(calc.pop().toDouble())));break;
                        case TAN:calc.push(new StackEle(Math.tan(calc.pop().toDouble())));break;
                        case COT:calc.push(new StackEle(1/Math.tan(calc.pop().toDouble())));break;
                        case factorial:calc.push(new StackEle(fac(calc.pop().toInt())));break;
                        default:break;
                    }
                    break;
            }
        }
        if(calc.size()!=1)
            throw new Exception("calc stack don't has one ele!");
        Double rs = calc.pop().toDouble();
        if(rs.isInfinite() || rs.isNaN()){
            res = 0.0;
            throw new Exception("cannot calculate,\r\ncheck your expression please.");
        }else {
            res = rs;
            String s = rs.toString();
            if(s.length()>11)
                s=s.substring(0,10);
            return s;
        }
    }

    private Integer fac(Integer i) throws Exception {
        if (i==0)
            return 0;
        if(i>12 || i<1)
            throw new Exception("out of factorial limit");
        return facc(i,1);
    }
    private Integer facc(Integer i,Integer sum){
        if(i<2)
            return sum;
        sum*=i;
        return facc(--i, sum);
    }

    /**
     * Stack element class.
     */
    private static class StackEle {
        type type = null;
        Integer num = 0;
        Double dbl = 0.0;
        opt optr = null;
        Boolean justCast = true;
        Integer endsWithZeros = 0;
        StackEle(Integer num){
            type = StackEx.type.INT;
            this.num = num;
        }
        StackEle(Double num){
            type = StackEx.type.DBL;
            dbl = num;
        }
        StackEle(opt opt){
            type = StackEx.type.OPT;
            optr = opt;
        }
        StackEle(type t) {type=t;}

        Boolean isNum(){return type.isNum();}
        Boolean isCalcNum(){return type.isCalcNum();}
//        Boolean isNum(){return (type != StackEx.type.OPT);}
//        Boolean isCalcNum(){return (type== StackEx.type.INT||type== StackEx.type.DBL);}
        Boolean isZero(){return ((type == StackEx.type.INT && num == 0) || (type == StackEx.type.DBL && dbl == 0.0));}

        Boolean addDot(){
            if(type== StackEx.type.INT){
                type = StackEx.type.DBL;
                dbl = num.doubleValue();
                num = null;
                return true;
            }else
                return false;
        }
        Boolean add(Integer i){
            switch (type){
                case INT:
                    num = num*10 +i;
                    break;
                case DBL:
                    if(i==0)
                        endsWithZeros++;
                    else {
                        String t = dbl.toString();
                        for (Integer j = 0;j<endsWithZeros;j++){t += "0";}
                        t += i;
                        endsWithZeros = 0;
                        if(justCast){
                            justCast=false;
                            dbl = Double.parseDouble(t.substring(0, t.length() - 2) + i.toString());
                        }else {
                            dbl = Double.parseDouble(t);
    //                        dbl = Double.parseDouble(dbl+i.toString());
                        }
                    }
                    break;
                default:
                    return false;
            }
            return true;
        }

        void removeEnding(){
            if(type == StackEx.type.INT){
                num = num/10;
            }else {
                if(justCast){
                    type= StackEx.type.INT;
                    num = dbl.intValue();
                    dbl = 0.0;
                }else {
                    String t = dbl.toString();
                    t = t.substring(0,t.length()-2);
                    if(t.endsWith(".")){
                        justCast=true;
                    }
                    dbl = Double.parseDouble(t);
                }
            }
        }

        Double toDouble(){return (type== StackEx.type.DBL) ? dbl : num.doubleValue();}
        Integer toInt(){return (type== StackEx.type.INT)? num : dbl.intValue();}
    }

    public enum opt{
        PLUS,MINUS,TIMES,DIVIDE,SQUARE,Lbracket,Rbracket,SIN,COS,TAN,COT,factorial;
        Boolean isEleArith(){return (
            this == StackEx.opt.PLUS ||
            this == StackEx.opt.MINUS ||
            this == StackEx.opt.TIMES ||
            this == StackEx.opt.DIVIDE);}
        Boolean isTriFunc(){return (
            this == StackEx.opt.SIN ||
            this == StackEx.opt.COS ||
            this == StackEx.opt.TAN ||
            this == StackEx.opt.COT);}
    }
    public enum type{
        INT,DBL,OPT,RND,E,PI;
        Boolean isOpt(){return this==OPT;}
        Boolean isNum(){return this!=OPT;}
        Boolean isCalcNum(){return (this==INT || this==DBL);}
    }
}