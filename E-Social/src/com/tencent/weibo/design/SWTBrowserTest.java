package com.tencent.weibo.design;


import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class SWTBrowserTest implements Listener
{
	private String url;
	private Text text;
	private Browser browser; 
	private Button button,button1;
	private Shell shell;
	
	public SWTBrowserTest(String url) {
		this.url=url;
	}
    public String init()
    {
    	
        Display display=new Display();
         shell=new Shell(display);
        shell.setText("SWT Browser Test");
        shell.setSize(500,600);
       
        shell.setLocation((shell.getMonitor().getBounds().width-shell.getSize().x)/2,(shell.getMonitor().getBounds().height-shell.getSize().y)/2);
         text=new Text(shell,SWT.BORDER);
        text.setBounds(40,5,120,25);  //&scope=read_user_status+publish_comment+read_user_feed+status_update
        text.setText("open.t.qq.com/cgi-bin/oauth2/authorize?client_id=" +
				"801143463&response_type=token&redirect_uri=http://t.qq.com/yinhen3");
         button=new Button(shell,SWT.BORDER);
        
        
        button.setBounds(165,5,25,25);        
        button.setText("go");
        Label label=new Label(shell,SWT.LEFT);
        label.setText("网址:");
        label.setBounds(5, 5, 25, 25);
        
       browser=new Browser(shell,SWT.FILL);
        browser.setBounds(5,30,500,550);
        browser.setUrl(url);
       // browser.setUrl("https://"+text.getText());
        button.addListener(SWT.Selection, new Listener()
        {
            public void handleEvent(Event event)
            {
                String input=text.getText().trim();
                if(input.length()==0)return;
	                if(!input.startsWith("http://"))
	                {
	                    input="https://"+input;
	                    text.setText(input);
	                }
	                browser.setUrl(input);
//                browser.execute("document.getElementById('email').value=document.cookie");
//                browser.execute("document.getElementById('password').value=document.cookie");
//                
            }
        });

         button1=new Button(shell,SWT.BORDER);
        button1.setBounds(190,5,45,25);        
        button1.setText("主界面");
        button1.addListener(SWT.Selection,this);
     
        shell.open();
        while (!shell.isDisposed()) {
        	
            if (!display.readAndDispatch())
              display.sleep();
          }

          display.dispose();
         return url;
    }
	@Override
	public void handleEvent(Event e) {
			url = browser.getUrl();
			//System.out.println("button1");
			MessageBox dialog=new MessageBox(shell,SWT.CANCEL|SWT.OK|SWT.ICON_INFORMATION);
			dialog.setText("登录成功");
			dialog.setMessage("按确定显示主界面.");
			if(SWT.OK == dialog.open()){
				shell.dispose();
			}
	}
}
