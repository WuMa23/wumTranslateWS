

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextArea;

/**
 * Servlet implementation class TranslationWebService
 */
public class TranslationWebService extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TranslationWebService() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		
		final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_17); 	// Instanzierung Browser/Web-Client
		webClient.getOptions().setJavaScriptEnabled(false);						// JavaScript deaktivieren
		webClient.getOptions().setCssEnabled(false);							// CSS deaktivieren
		
		HtmlPage page1 = webClient.getPage("https://translate.google.com");		// Web-Site Google Translate (gt) aufrufen
		out.println("aufgerufene Web-Site: " + page1.getTitleText());
		
		final HtmlSelect select_sl = page1.getHtmlElementById("gt-sl");			// SourceLanguage zuweisen
		final HtmlOption option_sl = select_sl.getOptionByValue("de");			// Option auf de (Deutsch) setzen
		select_sl.setSelectedAttribute(option_sl, true);
		
		final HtmlSelect select_tl = page1.getHtmlElementById("gt-tl");			// ToLanguage zuweisen
		final HtmlOption option_tl = select_tl.getOptionByValue("en");			// Option auf en (Englisch) setzen
		select_tl.setSelectedAttribute(option_tl, true);
		
		final HtmlTextArea source = page1.getHtmlElementById("source");			// Html-Element "source" zuweisen
		String toTranslate =  request.getParameter("toTranslate");
		if (toTranslate == null) {
			return;																// ToDo 
		} else {
			source.setText(toTranslate);										// Zu übersetzenden Text im Html-Element "source" eintragen
		}
			
		final HtmlSubmitInput button = page1.getHtmlElementById("gt-submit");	// Submit-Button zuweisen	
		final HtmlPage page2 = button.click();									// Button anklicken und Ergebnisseite zuweisen

		final HtmlSpan result_box = page2.getHtmlElementById("result_box");		// result_box für Übersetzungstext zuweisen 
		out.println("result_text: " + result_box.asText());						// Ausgabe Übersetzungstext
		
		webClient.closeAllWindows();											// Browser-Fenster schließen
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
