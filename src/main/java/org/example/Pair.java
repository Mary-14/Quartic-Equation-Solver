package org.example;

public class Pair implements Comparable<Pair>{
    double re;
    double im;

    public Pair(double re, double im){
        this.re=re;
        this.im=im;
    }

    public Pair(Pair other) {
        this.re = other.re;
        this.im = other.im;
    }

    @Override
    public int compareTo(Pair o) {
        if(Math.abs(this.re-o.re)<=0.0001){
            if(Math.abs(this.im-o.im)<=0.0001) {
                return 0;
            } else if(this.im-o.im>0.0001){
               return 1;
            } else return -1;
        }else if(this.re-o.re>0.0001){
             return 1;
        }else{
            return -1;
        }
    }
    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Pair pair = (Pair) obj;
        return Math.abs(this.re-pair.re)<0.0001&&Math.abs(this.im-pair.im)<0.0001;
    }

}

