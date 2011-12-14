

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.LayoutManager;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Time;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;


public class Sistema extends JFrame {

	private static final long serialVersionUID = 4845159616516818310L;
	private final int MENORVALORX = 400;
	private final int MENORVALORY = 300;
	private final int MAIORVALORX = 800;
	private final int MAIORVALORY = 600;
	
	
	private Desktop desktop;

	private Date aData;
	private Long aHora;
	private URI uri;

	private MenuBar barra = new MenuBar();
	private Menu menu = new Menu("Arquivo");
	private MenuItem home = new MenuItem("Principal");
	private MenuItem config = new MenuItem("Configuração");
	private MenuItem sair = new MenuItem("Sair");

	private JPanel principal;
	private JPanel tela2;
	private JPanel tela3;
	private JButton atualiza;
	private JButton registra;
	private JLabel hora;
	private Label data;

	private JPanel painel1;
	private JPanel painel2;
	private JRadioButton entrada, saida;
	private ButtonGroup estado;
	private String justificativa;
	private String assunto;
	private final JToolBar toolBar = new JToolBar();

	public Sistema() {
		super("Sistema de Ponto CNJ");
		setFont(null);
		setBackground(new Color(240, 240, 240));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMenuBar(barra);
		getContentPane().add(toolBar, BorderLayout.NORTH);
		
		/**
		 * Botoes e controladores
		 * TODO botoes
		 */
				atualiza = new JButton("Atualiza");
				ActionListener alAtualiza = new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						setaHora(new Date().getTime());
						hora.setText(new Time(getaHora()).toString());
					}
				};
				atualiza.addActionListener(alAtualiza);
				registra = new JButton("Registra");
				ActionListener alRegistra = new ActionListener() {

					public void actionPerformed(ActionEvent arg0) {

						if (Desktop.isDesktopSupported()) {
							desktop = Desktop.getDesktop();
							try {
								String nome;
								setaHora(new Date().getTime());
								hora.setText(new Time(getaHora()).toString());
								if (entrada.getSelectedObjects() != null) {
									nome = entrada.getText();
								} else {
									nome = saida.getText();
								}
								String subject = "?subject="+getAssunto();
								String apresenta = "&body=Registrando%20" + nome;
								String textoData = "&body=Data:"
										+ DateFormat.getDateInstance().format(
												getaData());
								String textoHora = "&body=Hora:"
										+ new Time(getaHora()).toString();
								String emBranco = "&body=%20";
								String textoJusto = "&body=Obs:"
										+ replaceStringUrl(getJustificativa());
								String Email = "mailto:pacalexandre@gmail.com"
										+ replaceStringUrl(subject)
										+ apresenta
										+ textoData
										+ textoHora
										+ emBranco
										+ replaceStringUrl(textoJusto)
										+ emBranco
										+ "&body=%20Novo%20Sistema%20de%20Ponto%20Atualizado%20v2.15.0321r";
										
								uri = new URI(Email);
								desktop.mail(uri);
								//System.out.println(nome + ": "+ DateFormat.getDateInstance().format(getaData()) + " | "+ new Time(getaHora()).toString());
							} catch (IOException e) {
								JOptionPane.showMessageDialog(painel2,
										"Problema na Mensagem \n" + e.getCause());
								e.printStackTrace();
							} catch (URISyntaxException e) {
								JOptionPane.showMessageDialog(painel2,
										"Problema na Mensagem \n" + e.getReason());
								e.printStackTrace();
							}
						}

					}
				};
				registra.addActionListener(alRegistra);

		/**
		 * Raiz principal das telas Container 
		 * TODO Base
		 */
		principal = new JPanel();
		principal.setLayout(new LayoutManager() {
			
			public void removeLayoutComponent(Component arg0) {
			}
			
			public Dimension preferredLayoutSize(Container arg0) {
				return null;
			}
			
			public Dimension minimumLayoutSize(Container arg0) {
				return new Dimension();
			}
			
			public void layoutContainer(Container arg0) {
			}
			
			public void addLayoutComponent(String arg0, Component arg1) {
			}
		});
		tela2 = new JPanel();
		tela2.setBounds(0, 0, 0, 0);
		tela3 = new JPanel();
		painel1 = new JPanel();
		painel2 = new JPanel();
		estado = new ButtonGroup();
		tela2.setVisible(false);
		
		tela3.setVisible(false);
		
		
		//paineis 
	
		painel1.setLayout(new GridLayout(3,2,0,0));
		painel1.setBackground(Color.WHITE);
		
		
		painel2.setLayout(new GridLayout(2, 1));

		data = new Label(DateFormat.getDateInstance().format(getaData()));
		data.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
		
		
		
		hora = new JLabel(new Time(getaHora()).toString());
		hora.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
		//radio
		entrada = new JRadioButton("Entrada");
		saida = new JRadioButton("Saida");
		estado.add(entrada);
		estado.add(saida);
		//hora
		getHorario();
		
		
		
		/**
		 * Menus com Actions
		 */
		menu.add(home);
		menu.add(config);
		menu.add(sair);
		barra.add(menu);
		
		ActionListener alSair = new Action() {
		
			public void actionPerformed(ActionEvent e) {
				dispose();
			}

			public void addPropertyChangeListener(PropertyChangeListener arg0) {
			}

			public Object getValue(String arg0) {
				return null;
			}

			public boolean isEnabled() {
				return false;
			}

			public void putValue(String arg0, Object arg1) {

			}

			public void removePropertyChangeListener(PropertyChangeListener arg0) {

			}

			public void setEnabled(boolean arg0) {

			}
		};

		sair.addActionListener(alSair);
		ActionListener alConfig = new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
						
			}
		};
		config.addActionListener(alConfig);
		ActionListener alHome = new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
			
				
			}
		};
		home.addActionListener(alHome);
		
	}

	
	/**
	 * Main 
	 * @param args
	 * TODO main
	 */
	public static void main(String[] args) {
		final Sistema entrada = new Sistema();
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				entrada.setVisible(true);
				entrada.setBounds(100, 100, 500, 370);
				entrada.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		});
	}
	
	public String replaceStringUrl(String string){	
		string = string.replace(" ", "%20");
		string = string.replace("ç", "%63");
		string = string.replace("á", "%61");
		string = string.replace("ã", "%61");
		string = string.replace("à", "%61");
		string = string.replace("é", "%65");
		string = string.replace("ê", "%65");
		string = string.replace("í", "%69");
		string = string.replace("õ", "%6F");
		string = string.replace("ó", "%6F");
		string = string.replace("ô", "%6F");
		string = string.replace("ú", "%75");
		string = string.replace("ù", "%75");
		return string;
	}
	
	public String getAssunto() {
		if (assunto == null) {
			assunto = new String("Registro de Ponto");
		}
		return assunto;
	}
	
	public void setAssunto(String assunto){
		assunto = this.assunto;
	}

	public String getJustificativa() {
		if (justificativa == null) {
			justificativa = new String("Sem Observações");
		}
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		justificativa = this.justificativa;
	}

	public void getHorario() {
		Time teste = new Time(getaHora());
		if (teste.getHours() < 13) {
			entrada.setSelected(true);
			saida.setSelected(false);
		} else {
			entrada.setSelected(false);
			saida.setSelected(true);
		}
	}

	public Date getaData() {
		if (aData == null) {
			aData = new Date();
		}
		return aData;

	}

	public void setaData(Date aData) {
		this.aData = aData;
	}

	public Long getaHora() {
		if (aHora == null) {
			aHora = new Date().getTime();
		}
		return aHora;
	}

	public void setaHora(Long aHora) {
		this.aHora = aHora;
	}
}
