package calc.frame;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JInternalFrame;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CalculadoraFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static final float CARGA_TRIBUTARIA = 30.0f / 100.0f;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CalculadoraFrame frame = new CalculadoraFrame();
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
	public CalculadoraFrame() {
		setTitle("Calculadora de Preço");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel title = new JLabel("Calculadora de Preço");
		title.setBounds(96, 10, 241, 34);
		title.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
		contentPane.add(title);
		
		JLabel salarioDesejadoLabel = new JLabel("Salário Desejado:");
		salarioDesejadoLabel.setBounds(43, 55, 100, 14);
		contentPane.add(salarioDesejadoLabel);
		
		JLabel horasTrabalhadasLabel = new JLabel("Horas trabalhadas ao mês:");
		horasTrabalhadasLabel.setBounds(203, 55, 155, 14);
		contentPane.add(horasTrabalhadasLabel);
		
		JLabel margemLucroLabel = new JLabel("Margem de Lucro:");
		margemLucroLabel.setBounds(43, 131, 112, 14);
		contentPane.add(margemLucroLabel);
		
		JLabel custoFixoLabel = new JLabel("Custos Fixos (água, energia, etc):");
		custoFixoLabel.setBounds(203, 131, 200, 14);
		contentPane.add(custoFixoLabel);
		
		JFormattedTextField salarioDesejadoInput = new JFormattedTextField();
		salarioDesejadoInput.setBounds(43, 80, 86, 20);
		contentPane.add(salarioDesejadoInput);
		
		JFormattedTextField margemLucroInput = new JFormattedTextField();
		margemLucroInput.setBounds(43, 156, 86, 20);
		contentPane.add(margemLucroInput);
		
		JFormattedTextField custoFixoInput = new JFormattedTextField();
		custoFixoInput.setBounds(203, 156, 91, 20);
		contentPane.add(custoFixoInput);
		
		JFormattedTextField horasTrabalhadasInput = new JFormattedTextField();
		horasTrabalhadasInput.setBounds(203, 80, 91, 20);
		contentPane.add(horasTrabalhadasInput);
		
		JButton limparCamposBtn = new JButton("Limpar Campos");
		limparCamposBtn.setFont(new Font("Tahoma", Font.BOLD, 11));
		limparCamposBtn.setBounds(10, 227, 133, 23);
		contentPane.add(limparCamposBtn);
		
		JButton calcularBtn = new JButton("Calcular");
		calcularBtn.setFont(new Font("Tahoma", Font.BOLD, 11));
		calcularBtn.setBounds(335, 227, 89, 23);
		contentPane.add(calcularBtn);
		
		JLabel resultadoLabel = new JLabel("Seu projeto deverá custar por mês: R$");
		resultadoLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		resultadoLabel.setBounds(43, 187, 241, 29);
		contentPane.add(resultadoLabel);
		
		JLabel showResultLabel = new JLabel("");
		showResultLabel.setBounds(289, 191, 103, 20);
		contentPane.add(showResultLabel);
		
		limparCamposBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				salarioDesejadoInput.setText("");
				margemLucroInput.setText("");
				custoFixoInput.setText("");
				horasTrabalhadasInput.setText("");
				showResultLabel.setText("");
			}
		});
		
		calcularBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				float salario = calculaSalarioEsperadoMaisCargaTributaria(salarioDesejadoInput.getText());
				float valorHoras = calculaValorHoraMes(horasTrabalhadasInput.getText(), salario);
				float custoFixo = calculaCustoFixoHora(custoFixoInput.getText(), horasTrabalhadasInput.getText());
				float valorMinimoCobrado = valorMinimoCobrado(valorHoras, custoFixo);
				float valorCustoOperacional = valorFinalCustoOperacional(valorMinimoCobrado, margemLucroInput.getText());
				float valorTotalCobrado = valorTotalCobrado(valorCustoOperacional, Float.parseFloat(horasTrabalhadasInput.getText()));
				
				showResultLabel.setText(String.format("%.2f", valorTotalCobrado));
			}
		});
	}
	
	
	private float calculaSalarioEsperadoMaisCargaTributaria(String valor) {
		float salarioEsperadoInformado = Float.parseFloat(valor);
		float result = salarioEsperadoInformado + (salarioEsperadoInformado * CARGA_TRIBUTARIA);
		
		return result;
	}
	
	private float calculaValorHoraMes(String valor, float salario) {
		float horas = Float.parseFloat(valor);
		float valorHoras = salario/horas;
		
		return valorHoras;
	}
	
	private float calculaCustoFixoHora(String custoFixo, String horas) {
		float valorCustoFixo = Float.parseFloat(custoFixo);
		float valorHoras = Float.parseFloat(horas);
		
		float result = valorCustoFixo/valorHoras;
		return result;
	}
	
	private float valorMinimoCobrado(float valorHoraTrabalho, float valorHoraCustoFixo) {
		return valorHoraTrabalho + valorHoraCustoFixo;
	}
	
	private float valorFinalCustoOperacional(float valorMinimo, String valor) {
		float margemLucro = Float.parseFloat(valor);
		float valorCustoOperacional = valorMinimo + (valorMinimo * margemLucro/100);
		
		return valorCustoOperacional;
	}
	
	private float valorTotalCobrado(float valorCustoOperacional, float horasTrabalhadas) {
		return valorCustoOperacional * horasTrabalhadas;
	}
}
