package com.mirzaakhena.koneksi;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")

/**
 * 
 * @author mirzaakhena@gmail.com
 *
 */
public class Main extends JFrame {

	private JPanel contentPane;
	private JTextField urlField;
	private JTextArea textArea;
	private JButton btnRestTemplate;
	private JButton btnClear;
	private JScrollPane scrollPane_1;
	private JTextPane textPane;
	private JButton btnWhatYouShould;

	public static void main(String[] args) throws UnirestException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});		
		
	}

	
	/**
	 * Create the frame.
	 */
	public Main() {
		setTitle("tagbatch test connecting");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(488, 520);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow]", "[][][200,grow][][200,grow][]"));
		
		btnWhatYouShould = new JButton("What You Should Prepare ");
		btnWhatYouShould.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JOptionPane.showMessageDialog(null, "1. Run the hmi web service on port 80\n2. Set up tag1, tag2, tag3 and tag4 in hmi");
				
			}
		});
		contentPane.add(btnWhatYouShould, "cell 0 0");

		urlField = new JTextField("http://localhost:80/tagbatch");
		contentPane.add(urlField, "flowx,cell 0 1,growx");
		urlField.setColumns(10);
		
		scrollPane_1 = new JScrollPane();
		contentPane.add(scrollPane_1, "cell 0 2,grow");
		
		textPane = new JTextPane();
		textPane.setText("{\n\"includeTagMetadata\": \"false\",\n\"getTags\": [\"Tag1\", \"Tag2\"],\n\"setTags\": [\n{\"name\": \"Tag3\", \"value\": \"77\"},\n{\"name\": \"Tag4\", \"value\": \"88\"}\n]\n}\n");
		scrollPane_1.setViewportView(textPane);

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, "cell 0 4,grow");

		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);

		btnRestTemplate = new JButton("connect with UniRest");
		btnRestTemplate.addActionListener(x -> konekWithUnirest());
		contentPane.add(btnRestTemplate, "cell 0 3");

		btnClear = new JButton("Clear");
		btnClear.addActionListener(x -> textArea.setText(""));
		contentPane.add(btnClear, "cell 0 5");
	}

	private void konekWithUnirest() {
		try {
			HttpResponse<String> response = Unirest//
					.post(urlField.getText())//
					.header("Content-Type", "application/json")//
					.body(textPane.getText().getBytes())//
					.asString();

			textArea.append(response.getStatusText());
			textArea.append(response.getBody());

		} catch (UnirestException e) {

			e.printStackTrace();
		}

	}

}
