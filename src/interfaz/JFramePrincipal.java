package interfaz;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import logica.FCFS;
import logica.ProcesosDisponibles;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JToggleButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EtchedBorder;

@SuppressWarnings("serial")
public class JFramePrincipal extends JFrame implements ActionListener{

	private int algoritmo = 1;				//Modelo seleccionado
	private JPanel panelPrincipal;
	
	private JPanel panelProcesos;
	private JToggleButton tglbtnON_OFF;
	private JLabel lblProcesosDisp;
	private JScrollPane scrollLista;
	private JList<String> listaProcesos;
	private JLabel lblTLegada;
	private JSpinner spnTLlegada;
	//Interrupcion
	private JCheckBox chckbxInterrupcion;
	private JLabel lblIInterrup;
	private JSpinner spnIInterrup;
	private JLabel lblTInterrup;
	private JSpinner spnTInterrup;
	private JButton btnAgregarProceso;
	
	private JPanel panelModPlanificacion;
	private ButtonGroup btgModMemoria;
	private JRadioButton rdbtnFCFS;
	private JRadioButton rdbtnSPN;
	private JRadioButton rdbtnSRTF;
	private JRadioButton rdbtnRR;
	private JRadioButton rdbtnDerPreferente;
	private JCheckBox chckbxRetroalimentacion;
	
	private JPanel panelTabla;
	private DefaultTableModel modeloTabla;
	
	private JTable tablaProcesos;
	private JScrollPane scrollTabla;
	private String[] nombreColumna = {"Proceso", "Llegada", "T. Ejecucion", "Inicio B.", "Duracion B.", "T. Espera", "T. Bloqueo"};
	private Object[][] procesoModelo = new Object[0][7];
	
	private PanelDibujoProc dibujoProcesos;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public JFramePrincipal() {
		setTitle("Simulador Planificacion de Procesos");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(50, 50, 1000, 700);
		
		panelPrincipal = new JPanel();
		panelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelPrincipal);
		panelPrincipal.setLayout(null);
		
		//Panel de seleccion de procesos
		panelProcesos = new JPanel();
		panelProcesos.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelProcesos.setBounds(10, 10, 966, 223);
		panelPrincipal.add(panelProcesos);
		panelProcesos.setLayout(null);
		
		tglbtnON_OFF = new JToggleButton();
		tglbtnON_OFF.setSelectedIcon(new ImageIcon(JFramePrincipal.class.getResource("/img/detener.png")));
		tglbtnON_OFF.setIcon(new ImageIcon(JFramePrincipal.class.getResource("/img/iniciar.png")));
		tglbtnON_OFF.setForeground(Color.WHITE);
		tglbtnON_OFF.setBounds(34, 175, 100, 30);
		tglbtnON_OFF.addActionListener(this);
		panelProcesos.add(tglbtnON_OFF);
		
		lblProcesosDisp = new JLabel("Proc. Disponibles");
		lblProcesosDisp.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblProcesosDisp.setBounds(10, 10, 142, 13);
		panelProcesos.add(lblProcesosDisp);
		
		listaProcesos = new JList(generarListaProcesos());
		listaProcesos.setSelectedIndex(0);
		listaProcesos.setFont(new Font("Tahoma", Font.PLAIN, 10));
		listaProcesos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		scrollLista = new JScrollPane();
		scrollLista.setViewportView(listaProcesos);
		scrollLista.setBounds(10, 33, 160, 123);
		panelProcesos.add(scrollLista);
		
		lblTLegada = new JLabel("Tiempo Llegada");
		lblTLegada.setBounds(247, 24, 96, 13);
		panelProcesos.add(lblTLegada);
		
		spnTLlegada = new JSpinner();
		spnTLlegada.setModel(new SpinnerNumberModel(Integer.valueOf(0), null, null, Integer.valueOf(1)));
		spnTLlegada.setToolTipText("Tiempo de llegada");
		spnTLlegada.setFont(new Font("Tahoma", Font.PLAIN, 10));
		spnTLlegada.setBounds(257, 43, 46, 19);
		panelProcesos.add(spnTLlegada);
		
		
		//Botones interrupcion
		chckbxInterrupcion = new JCheckBox("<html>Interrupci\u00F3n</html>");
		chckbxInterrupcion.setFont(new Font("Tahoma", Font.BOLD, 11));
		chckbxInterrupcion.setBounds(257, 80, 100, 19);
		panelProcesos.add(chckbxInterrupcion);
		
		lblIInterrup = new JLabel("Inicio Interrupci\u00F3n");
		lblIInterrup.setBounds(267, 105, 108, 19);
		panelProcesos.add(lblIInterrup);
		
		spnIInterrup = new JSpinner();
		spnIInterrup.setModel(new SpinnerNumberModel(Integer.valueOf(0), null, null, Integer.valueOf(1)));
		spnIInterrup.setToolTipText("Tiempo de Inicio");
		spnIInterrup.setFont(new Font("Tahoma", Font.PLAIN, 10));
		spnIInterrup.setBounds(289, 123, 46, 19);
		panelProcesos.add(spnIInterrup);
		
		lblTInterrup = new JLabel("Tiem. Interrupci\u00F3n");
		lblTInterrup.setBounds(384, 105, 125, 19);
		panelProcesos.add(lblTInterrup);
		
		spnTInterrup = new JSpinner();
		spnTInterrup.setModel(new SpinnerNumberModel(Integer.valueOf(0), null, null, Integer.valueOf(1)));
		spnTInterrup.setToolTipText("Tiempo de Fin");
		spnTInterrup.setFont(new Font("Tahoma", Font.PLAIN, 10));
		spnTInterrup.setBounds(404, 123, 46, 19);
		panelProcesos.add(spnTInterrup);
		
		
		//Panel Modelos de planificacion
		panelModPlanificacion = new JPanel();
		panelModPlanificacion.setBounds(638, 10, 213, 203);
		panelProcesos.add(panelModPlanificacion);
		panelModPlanificacion.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Algoritmo de Planificaci\u00F3n", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelModPlanificacion.setLayout(null);
		
		rdbtnFCFS = new JRadioButton("<html>FCFS \u2013 First come, first<br />served</html>");
		rdbtnFCFS.setSelected(true);
		rdbtnFCFS.setBounds(10, 25, 197, 29);
		panelModPlanificacion.add(rdbtnFCFS);
		
		rdbtnSPN = new JRadioButton("<html>SPN \u2013 Shorted process<br />next</html>");
		rdbtnSPN.setBounds(10, 56, 197, 29);
		panelModPlanificacion.add(rdbtnSPN);
		
		rdbtnSRTF = new JRadioButton("<html>SRTF \u2013 Shortest remaining <br />time first</html>");
		rdbtnSRTF.setBounds(10, 87, 197, 29);
		panelModPlanificacion.add(rdbtnSRTF);
			
		rdbtnRR = new JRadioButton("<html>RR \u2013 Round Robin</html>");
		rdbtnRR.setBounds(10, 118, 197, 21);
		panelModPlanificacion.add(rdbtnRR);
		
		rdbtnDerPreferente = new JRadioButton("<html>Derecho preferente</html>");
		rdbtnDerPreferente.setBounds(10, 141, 197, 21);
		panelModPlanificacion.add(rdbtnDerPreferente);
		
		chckbxRetroalimentacion = new JCheckBox("<html>Retroalimentacion</html>");
		chckbxRetroalimentacion.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chckbxRetroalimentacion.setBounds(35, 164, 172, 21);
		panelModPlanificacion.add(chckbxRetroalimentacion);
		
		//Agrega botones seleccion de proceso a un grupo
		btgModMemoria = new ButtonGroup();
		btgModMemoria.add(rdbtnFCFS);
		btgModMemoria.add(rdbtnSPN);
		btgModMemoria.add(rdbtnSRTF);
		btgModMemoria.add(rdbtnRR);
		btgModMemoria.add(rdbtnDerPreferente);
		
		btnAgregarProceso = new JButton("A\u00F1adir Proceso");
		btnAgregarProceso.setBounds(304, 171, 134, 21);
		btnAgregarProceso.addActionListener(this);
		panelProcesos.add(btnAgregarProceso);
		
		panelTabla = new JPanel();
		panelTabla.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelTabla.setBounds(10, 243, 966, 156);
		panelPrincipal.add(panelTabla);
			
		//Variables para la creacion de la tabla de procesos
		modeloTabla = new DefaultTableModel(procesoModelo,  nombreColumna);
		panelTabla.setLayout(null);
		tablaProcesos = new JTable(modeloTabla);
		
		scrollTabla = new JScrollPane();
		scrollTabla.setViewportView(tablaProcesos);
		scrollTabla.setBounds(0, 0, 966, 156);
		panelTabla.add(scrollTabla);
		
		//Panel Dubujo de procesos
		/*panelDibujo = new JPanel();
		panelDibujo.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelDibujo.setBounds(10, 413, 876, 190);
		panelPrincipal.add(panelDibujo);
		panelDibujo.setLayout(null);
		*/
		//dibujoProcesos();
		
	}
	
	//Dibujar particiones
	public void dibujoProcesos() {

		dibujoProcesos = new PanelDibujoProc(algoritmo, modeloTabla);
		dibujoProcesos.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelPrincipal.add(dibujoProcesos);
		dibujoProcesos.setBounds(10, 413, 966, 240);
		dibujoProcesos.setLayout(null);
		
		

	}
	
	//Manejo eventos
	public void actionPerformed(ActionEvent event) {
		
		//Boton iniciar/detener
		if(event.getSource() == tglbtnON_OFF){
					
			JToggleButton tglbON_OFF = (JToggleButton)event.getSource();
			
            if(tglbON_OFF.isSelected()){
            	
            	//Modelo seleccionado
            	if(rdbtnFCFS.isSelected())
            		algoritmo = 1;
            	else if(rdbtnSPN.isSelected())
            		algoritmo = 2;
            	else if(rdbtnSRTF.isSelected())
            		algoritmo = 3;
            	else if(rdbtnRR.isSelected())
            		algoritmo = 4;
            	else if(rdbtnDerPreferente.isSelected())
            		algoritmo = 5;
            	
            	dibujoProcesos();
            	
            }else {
            	//habilitarDetenido();
            	this.dispose();
            	JFramePrincipal frame = new JFramePrincipal();
            	frame.setVisible(true);
            }
	
		}
		//Boton Agregar proceso
		if(event.getSource() == btnAgregarProceso) { 	
        	agregarPoceso();
		}
		
	}
		
	/**
	 * Genera la lista de procesos disponibles
	 * @return Arreglo de String con procesos disponibles
	 */
	public String[] generarListaProcesos() {
		ProcesosDisponibles procesosDisponibles = new ProcesosDisponibles();
		
		String listaProcesos[] = new String[procesosDisponibles.getDisponibles().length];
		
		for (int i = 0; i < listaProcesos.length; i++ ) {
			listaProcesos[i] = procesosDisponibles.getDisponibles()[i].getPID() + " - " + 
							   procesosDisponibles.getDisponibles()[i].getNombre() 
							   + " - Uso CPU:"+ procesosDisponibles.getDisponibles()[i].gettEjecucion() ;
		}
		
		return listaProcesos;
	}
	
	//Generar lista de procesos activos
	public void agregarPoceso() {
		ProcesosDisponibles procesosDisponibles = new ProcesosDisponibles();
		
		int seleccionado = listaProcesos.getSelectedIndex();
		procesoModelo = new Object[1][7];
		
		//Insertando en la tabla de procesos
		procesoModelo[0][0] = procesosDisponibles.getDisponibles()[seleccionado].getNombre();
		procesoModelo[0][1] = (int)spnTLlegada.getValue();
		procesoModelo[0][2] = procesosDisponibles.getDisponibles()[seleccionado].gettEjecucion();
		procesoModelo[0][3] = (int)spnIInterrup.getValue();
		procesoModelo[0][4] = (int)spnTInterrup.getValue();
		procesoModelo[0][5] = 0;
		procesoModelo[0][6] = 0;
		modeloTabla.addRow(procesoModelo[0]);
		
	}
	
	/**
	 * Desabilitar paneles que no deben estar activos
	 */
	public void algoritmoFCFS() {
		new FCFS(modeloTabla);
	}
	
	/**
	 * Desabilitar paneles que no deben estar activos
	 */
	public void desabilitarIniciado() {
		rdbtnFCFS.setEnabled(false);
		rdbtnSPN.setEnabled(false);
		rdbtnSRTF.setEnabled(false);
		chckbxRetroalimentacion.setEnabled(false);
		rdbtnRR.setEnabled(false);
		rdbtnDerPreferente.setEnabled(false);
	}
	
	
	/**
	 * Habilitar paneles que deben estar activos
	 */
	public void habilitarDetenido() {
		rdbtnFCFS.setEnabled(true);
		rdbtnSPN.setEnabled(true);
		rdbtnSRTF.setEnabled(true);
		chckbxRetroalimentacion.setEnabled(true);
		rdbtnRR.setEnabled(true);
		rdbtnDerPreferente.setEnabled(true);
	}
	


}
