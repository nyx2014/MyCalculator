package snc.lsr.mycalculator;

import java.util.ArrayList;
import java.util.EmptyStackException;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.*;
import android.widget.Button;
import android.widget.TextView;
import snc.lsr.util.MToast;
import snc.lsr.util.StackEx;

public class Main extends Activity implements View.OnClickListener{
    /** Called when the activity is first created. */
	private SharedPreferences mPreferences;
	private boolean isExit =false;
	public MToast t = new MToast(this);

	TextView res,input;
	StackEx mCalc;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if(savedInstanceState != null) {
//            if (savedInstanceState.getString(res, -1) != -1) {
//              mThemeId = savedInstanceState.getInt("theme");
//              this.setTheme(mThemeId);
//            }
//        }



		overridePendingTransition(R.anim.in, R.anim.foad);
		if(mPreferences==null){
			mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		}
		//
		if (mPreferences.getBoolean("bNight", false)){
//			t.t("夜间模式更加护眼~");
			this.setTheme(R.style.AppTheme_Dark);
		}else{
			this.setTheme(R.style.AppTheme_Light);
		}
        setContentView(R.layout.main);

		ViewPager mViewPager = (ViewPager) findViewById(R.id.viewpager);
        
        //将要分页显示的View装入数组中
        LayoutInflater mLi = LayoutInflater.from(this);
        View view1 = mLi.inflate(R.layout.activity_main, null);
        View view2 = mLi.inflate(R.layout.activity_adv, null);
//        View view3 = mLi.inflate(R.layout.guide_3, null);
        
        //每个页面的Title数据
        final ArrayList<View> views = new ArrayList<View>();
        views.add(view1);
        views.add(view2);
//        views.add(view3);

        //填充ViewPager的数据适配器
        PagerAdapter mPagerAdapter = new PagerAdapter() {
			
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}
			
			@Override
			public int getCount() {
				return views.size();
			}

			@Override
			public void destroyItem(View container, int position, Object object) {
				((ViewPager)container).removeView(views.get(position));
			}

			@Override
			public Object instantiateItem(View container, int position) {
				((ViewPager)container).addView(views.get(position));
				return views.get(position);
			}
		};
		
		mViewPager.setAdapter(mPagerAdapter);

		res = (TextView)findViewById(R.id.textView1);
		input = (TextView)findViewById(R.id.textView2);

		try {
			String savedRes = mPreferences.getString("res","");
			res.setText(savedRes);

			if(!savedRes.equals(""))
				mCalc = new StackEx(savedRes);
			else
				mCalc = new StackEx();
		} catch (Exception e){
			e.printStackTrace();
			t.t(e.getMessage());
			t.t(e.getCause().toString());
			t.t("please contact author!");
		}

		final ArrayList<Button> Buttons = new ArrayList<>();
		Buttons.add((Button)view1.findViewById(R.id.button1));
		Buttons.add((Button)view1.findViewById(R.id.button2));
		Buttons.add((Button)view1.findViewById(R.id.button3));
		Buttons.add((Button)view1.findViewById(R.id.button4));
		Buttons.add((Button)view1.findViewById(R.id.button5));
		Buttons.add((Button)view1.findViewById(R.id.button6));
		Buttons.add((Button)view1.findViewById(R.id.button7));
		Buttons.add((Button)view1.findViewById(R.id.button8));
		Buttons.add((Button)view1.findViewById(R.id.button9));
		Buttons.add((Button)view1.findViewById(R.id.button10));
		Buttons.add((Button)view1.findViewById(R.id.button11));
		Buttons.add((Button)view1.findViewById(R.id.button12));
		Buttons.add((Button)view1.findViewById(R.id.button13));
		Buttons.add((Button)view1.findViewById(R.id.button14));
		Buttons.add((Button)view1.findViewById(R.id.button15));
		Buttons.add((Button)view1.findViewById(R.id.button16));
		Buttons.add((Button)view1.findViewById(R.id.button17));
		Buttons.add((Button)view1.findViewById(R.id.button18));

		Buttons.add((Button)view2.findViewById(R.id.button1));
		Buttons.add((Button)view2.findViewById(R.id.button2));
		Buttons.add((Button)view2.findViewById(R.id.button3));
		Buttons.add((Button)view2.findViewById(R.id.button4));
		Buttons.add((Button)view2.findViewById(R.id.button5));
		Buttons.add((Button)view2.findViewById(R.id.button6));
		Buttons.add((Button)view2.findViewById(R.id.button7));
		Buttons.add((Button)view2.findViewById(R.id.button8));
		Buttons.add((Button)view2.findViewById(R.id.button9));
		Buttons.add((Button)view2.findViewById(R.id.button10));
		Buttons.add((Button)view2.findViewById(R.id.button11));
		Buttons.add((Button)view2.findViewById(R.id.button12));
		Buttons.add((Button)view2.findViewById(R.id.button13));
		Buttons.add((Button)view2.findViewById(R.id.button14));
		Buttons.add((Button)view2.findViewById(R.id.button15));
		Buttons.add((Button)view2.findViewById(R.id.button16));

		Buttons.add((Button)view2.findViewById(R.id.button18));

		Button value = null;
		int size = Buttons.size();
		for (int i=0; i<size; i++) {
			value = (Button)Buttons.get(i);
			value.setTag(i);
			value.setOnClickListener(this);
		}
		//beauty but can't use in Android.
//		Buttons.get(17).setOnLongClickListener(v -> {
//            mCalc.clear();
//            res.setText("");
//            input.setText("");
//            return true;
//        });
		Buttons.get(17).setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				mCalc.clear();
				res.setText("");
				input.setText("");
				return true;
			}
		});
		Buttons.get(34).setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				mCalc.clear();
				res.setText("");
				input.setText("");
				return true;
			}
		});
    }

	public void onSwitchMode(View v){
		SharedPreferences.Editor editor = mPreferences.edit();
		if (mPreferences.getBoolean("bNight", false)){
			editor.putBoolean("bNight", false).commit();//immediately;
		}else{
			editor.putBoolean("bNight", true).apply();//background;
		}
		this.recreate();
	}

	@Override
	public void onClick(View v){
		int id = (Integer)v.getTag();
		String text = input.getText().toString();
		switch (id){
			case 0:addNum(7);break;
			case 1:addNum(8);break;
			case 2:addNum(9);break;
			case 3:addOpt(StackEx.opt.DIVIDE);break;
			case 4:addNum(4);break;
			case 5:addNum(5);break;
			case 6:addNum(6);break;
			case 7:addOpt(StackEx.opt.TIMES);break;
			case 8:addNum(1);break;
			case 9:addNum(2);break;
			case 10:addNum(3);break;
			case 11:addOpt(StackEx.opt.MINUS);break;
			case 12:appendInput(mCalc.addRes());break;
			case 13:addNum(0);break;
			case 14:if(!input.getText().toString().endsWith(".") && mCalc.addDot()){appendInput(".");}break;
			case 15:addOpt(StackEx.opt.PLUS);break;
			case 16:
				try {
					res.setText(mCalc.calc());
				} catch (StackOverflowError e) {
					t.t("Stack Overflow!");
				} catch (EmptyStackException e){
					t.t("expression contains error!");
				} catch (Exception e) {
					t.t(e.getMessage());
					e.printStackTrace();
				}
				saveRes(res.getText().toString());break;
			case 17:removeEnding(text);break;
			case 18:addOpt(StackEx.opt.Lbracket);break;
			case 19:addOpt(StackEx.opt.Rbracket);break;
			case 20:addOpt(StackEx.opt.SQUARE);break;
			case 21:if(mCalc.addNum(StackEx.type.E))appendInput("e");break;
			case 22:addOpt(StackEx.opt.COS);break;
			case 23:addOpt(StackEx.opt.SIN);break;
			case 24:addOpt(StackEx.opt.factorial);break;
			case 25:if(mCalc.addNum(StackEx.type.PI))appendInput("π");break;
			case 26:addOpt(StackEx.opt.TAN);break;
			case 27:addOpt(StackEx.opt.COT);break;
			case 28:if(mCalc.addNum(StackEx.type.RND))appendInput("Rnd");break;
//			case 29:addOpt(StackEx.opt.SIN);break;
//			case 30:addOpt(StackEx.opt.SIN);break;
//			case 31:addOpt(StackEx.opt.SIN);break;
//			case 32:addOpt(StackEx.opt.SIN);break;
//			case 33:addOpt(StackEx.opt.SIN);break;
			case 34:removeEnding(text);break;
			default:
				appendInput("?");
				t.t("此功能正在开发中~");break;
		}
	}

	private void removeEnding(String text) {
		if(mCalc.removeEnding()) {
			if(text.endsWith("sin(") || text.endsWith("cos(") || text.endsWith("tan(") || text.endsWith("cot("))
				input.setText(text.substring(0,text.length()-4));
			else if(text.endsWith("Rnd"))
				input.setText(text.substring(0,text.length()-3));
			else if(text.endsWith("√￣"))
				input.setText(text.substring(0,text.length()-2));
			else
				input.setText(text.substring(0,text.length()-1));
		}
		else input.setText("");
	}

	private void addNum(Integer i){if(mCalc.addNum(i)){appendInput(i + "");}}
	private void addOpt(StackEx.opt opt){
		String te = input.getText().toString();
		if(!mCalc.addOpt(opt)){switch (opt){
			case PLUS:changeEnding('+');break;
			case MINUS:changeEnding('-');break;
			case TIMES:changeEnding('×');break;
			case DIVIDE:changeEnding('÷');break;
			case SQUARE:changeEnding("√￣");break;
			case Lbracket:changeEnding('(');break;
			case Rbracket:changeEnding(')');break;
			case SIN:changeEnding("sin(");break;
			case COS:changeEnding("cos(");break;
			case TAN:changeEnding("tan(");break;
			case COT:changeEnding("cot(");break;
			case factorial:changeEnding('!');break;
			default:changeEnding('?');break;
		}}else {switch (opt){
			case PLUS:appendInput("+");break;
			case MINUS:appendInput("-");break;
			case TIMES:appendInput("×");break;//if(input.getText().toString().equals("")){appendInput("0×");}else
			case DIVIDE:appendInput("÷");break;
			case SQUARE:appendInput("√￣");break;
			case Lbracket:appendInput("(");break;
			case Rbracket:appendInput(")");break;
			case SIN:appendInput("sin(");break;
			case COS:appendInput("cos(");break;
			case TAN:appendInput("tan(");break;
			case COT:appendInput("cot(");break;
			case factorial:appendInput("!");break;
			default:appendInput("?");break;
		}}
	}

	private void appendInput(String t){
		String c = input.getText().toString();
		if((c.equals("")&&(t.equals("×")||t.equals("÷")||t.equals("!"))))
			input.setText(new StringBuilder().append("0").append(t).toString());
		else if(!c.endsWith("0") || t =="."|| t.equals("×")||t.equals("÷")||t.equals("!")||t.equals("+")||t.equals("-"))
			input.setText(new StringBuilder().append(input.getText()).append(t));
		else if(c.length()==1)
			input.setText(t);
		else {switch (c.substring(c.length()-2,c.length()-1)){
			case "":case "+":case "-":case "×":case "÷":case "￣":
				input.setText(c.substring(0, c.length() - 1) + t);break;
			default:
				input.setText(new StringBuilder().append(input.getText()).append(t));
		}}
	}

	private void changeEnding(char c){
		String t = input.getText().toString();
		if(t.equals("")&&(c=='!'||c=='×'||c=='÷'))
			input.setText(c);
		if(t.equals(""))return;
		input.setText(t.substring(0,t.length()-1)+c);
	}
	private void changeEnding(String c){
		String t = input.getText().toString();
		if(t.equals(""))return;
		input.setText(t.substring(0,t.length()-c.length())+c);
	}

	private void saveRes(String res){(mPreferences.edit()).putString("res",res).apply();}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		return id == R.id.action_settings || super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exit();
			return false;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	private void exit() {
		if (!isExit) {
			isExit = true;
			t.t("再按一次退出程序");
			mHandler.sendEmptyMessageDelayed(0, 2000);
		} else {
            /*
        	Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            System.exit(0);*/
			finish();
		}
	}

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			isExit = false;
		}
	};

	@Override
	protected void onPause(){
		super.onPause();
		overridePendingTransition(R.anim.in, R.anim.foad);
	}
}