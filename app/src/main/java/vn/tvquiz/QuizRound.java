package vn.tvquiz;
/** Persisted by the service after every answer; mistakes do not erase correct answers. */
final class QuizRound {
 final int required;int correct,index;
 QuizRound(int required,int correct,int index){this.required=Math.max(1,Math.min(20,required));this.correct=Math.max(0,Math.min(this.required,correct));this.index=Math.max(0,index);}
 boolean answer(boolean right){if(right&&correct<required)correct++;index++;return correct>=required;}
}
