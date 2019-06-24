package com.diccionariobd.diccionariodatos.reportes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.diccionariobd.diccionariodatos.dao.dto.AbstracDTO;
import com.diccionariobd.diccionariodatos.dao.dto.CamposTablasDTO;
import com.diccionariobd.diccionariodatos.dao.dto.ImpresionDTO;
import com.diccionariobd.diccionariodatos.dao.dto.TablasDTO;
import com.diccionariobd.diccionariodatos.ui.columnas.ColumnasTablasPresenter;
import com.diccionariobd.diccionariodatos.ui.tablas.TablasView;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTable;
import com.lowagie.text.pdf.PdfWriter;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.UI;

@Component
@Scope("prototype")
public class ReportePDF implements StreamSource {
	
	private static final Logger log = LoggerFactory.getLogger(ReportePDF.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ByteArrayOutputStream buf = null; //new ByteArrayOutputStream();
	private  TablasDTO tablasDTO = new TablasDTO();
    
	@Autowired
	private ColumnasTablasPresenter presenterColumnas;
    
    Font tblHeaderFont = new Font(Font.HELVETICA, 8f, Font.BOLD);
    Font tblHeaderTableFont = new Font(Font.COURIER, 8f, Font.BOLD);
    Font tblBodyTableFont = new Font(Font.COURIER, 6f, Font.NORMAL);
    Image img = null;
    
    
	
	public ReportePDF(){

	}
	
	public ReportePDF(ImpresionDTO data) throws Throwable{
		buf = new ByteArrayOutputStream();
		createPDF(data, buf,null);
	}
	
	public ReportePDF(ImpresionDTO data, String opcion) throws Throwable{
		buf = new ByteArrayOutputStream();
		createPDF(data, buf,opcion);
	}
	
	public ReportePDF(List<TablasDTO> data,ColumnasTablasPresenter presenterColumnas) throws Throwable{
		buf = new ByteArrayOutputStream();
		this.setPresenterColumnas(presenterColumnas);
		crearDiccionario(data, buf);
	}	
	
	
	
	
	public void print(ImpresionDTO data) throws Throwable
	{
		//ByteArrayOutputStream buf = new ByteArrayOutputStream();
		//createPDF(data, buf);
		//byte buffer[] = buf.toByteArray();
		
 
		
		/*HttpServletResponse res = null;//getResponse();
		res.setBufferSize(buffer.length);
		res.setContentType("application/pdf");
		res.setContentLength(buffer.length);
		ServletOutputStream out = res.getOutputStream();
		out.write(buffer);
		*/
	}
	
	
	protected void createPDF(ImpresionDTO data,ByteArrayOutputStream buf,String opcion) throws Throwable
	{
		
		if (opcion=="C"){
			crearPDFColumnas(data);
		}

		if (opcion=="T"){
			crearPDFTablas(data);
		}
		
	}


	private void crearPDFTablas(ImpresionDTO data) throws Throwable {
		//pdf objects
		Document doc = new Document();
		PdfWriter docWriter = PdfWriter.getInstance(doc, buf);

		//header
		HeaderFooter header = new HeaderFooter(new Phrase("PruebaHeader"), false);
		header.setBorder(Rectangle.BOTTOM);
		header.setAlignment(Rectangle.ALIGN_CENTER);
		doc.setHeader(header);
			
		//footer
		HeaderFooter footer = new HeaderFooter(new Phrase("PruebaFooter"), true);
		footer.setBorder(Rectangle.TOP);
		footer.setAlignment(Rectangle.ALIGN_RIGHT);
		doc.setFooter(footer);
			
		//pagesize
		doc.setPageSize(PageSize.LETTER);

		doc.open();
			
			//title
			Paragraph t = new Paragraph("Titulo del reporte",new Font(Font.HELVETICA, 18f));
			t.setAlignment(Rectangle.ALIGN_CENTER);
			doc.add(t);
			
			//paragraph
			Paragraph p = new Paragraph("Hello World");
			p.setAlignment(Rectangle.ALIGN_CENTER);
			doc.add(p);
			
		doc.close();
		docWriter.close();

		
	}

	private void crearPDFColumnas(ImpresionDTO data) throws Throwable {
		
		for (Iterator iterator = data.getListaCamposTablasDTO().iterator(); iterator.hasNext();) {
			CamposTablasDTO camposTablasDTO = (CamposTablasDTO) iterator.next();
			
			System.out.println("Desde PDFCOLumnas Tabla ID:" + camposTablasDTO.getIdTabla());
			System.out.println("Desde PDFCOLumnas Campo id:" + camposTablasDTO.getIdCampo());
			System.out.println("Desde PDFCOLumnas Nombre Campo:" + camposTablasDTO.getNameCampo());
			
		}

		
		//pdf objects
		Document doc = new Document();
		PdfWriter docWriter = PdfWriter.getInstance(doc, buf);

		//header
		HeaderFooter header = new HeaderFooter(new Phrase("HeaderCamposTablas" + data.getTablas().getNombreTabla()), false);
		header.setBorder(Rectangle.BOTTOM);
		header.setAlignment(Rectangle.ALIGN_CENTER);
		doc.setHeader(header);
			
		//footer
		HeaderFooter footer = new HeaderFooter(new Phrase("Footer Campos Tabkas "), true);
		footer.setBorder(Rectangle.TOP);
		footer.setAlignment(Rectangle.ALIGN_RIGHT);
		doc.setFooter(footer);
			
		//pagesize
		doc.setPageSize(PageSize.LETTER);

		doc.open();
			
			//title
			Paragraph t = new Paragraph("Titulo del reporte",new Font(Font.HELVETICA, 18f));
			t.setAlignment(Rectangle.ALIGN_CENTER);
			doc.add(t);
			
			//paragraph
			Paragraph p = new Paragraph("Campos Tbalas");
			p.setAlignment(Rectangle.ALIGN_CENTER);
			doc.add(p);
			
		doc.close();
		docWriter.close();

		
	}
	
	public void crearDiccionario(List<TablasDTO> listTablas,ByteArrayOutputStream buf) throws Throwable{
		//pdf objects
		Document doc = new Document();
		PdfWriter docWriter = PdfWriter.getInstance(doc, buf);
		doc.setMargins(15,15,15,15);
		
		//header
		/*
		HeaderFooter header = new HeaderFooter(new Phrase("Diccionario de Credito: " + tablasDTO.getNombreTabla()), false);
		header.setBorder(Rectangle.BOTTOM);
		header.setAlignment(Rectangle.ALIGN_CENTER);
		doc.setHeader(header);
		*/		
		
		//footer
		HeaderFooter footer = new HeaderFooter(new Phrase("Diccionario de Datos V.1 "), true);
		footer.setBorder(Rectangle.TOP);
		footer.setAlignment(Rectangle.ALIGN_RIGHT);
		doc.setFooter(footer);
		
		//pagesize
		doc.setPageSize(PageSize.LETTER);
		doc.open();
		
		//logo
    	String logoPath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
    	int contPaginas = 0;
    	
    	log.debug("path: "  + logoPath);
    	
    	

		//img = Image.getInstance(this.callLocalAction(logoPath));
    	img = Image.getInstance(logoPath + "/VAADIN/themes/persephone/img/LogoBancaribe.PNG");
		img.scalePercent(100);
		float imgY = doc.top() - img.getHeight();
		float imgX = doc.left();
		img.setAbsolutePosition(imgX, imgY);
		//doc.add(img);
		
	

		//Insertar Cabecera
		doc.add(cabeceraDiccionario());
		
		doc.add(cabecera2());
		
		//Registro de Modificaciones
		Paragraph t = new Paragraph("Registro de Modificaciones",new Font(Font.HELVETICA, 10f));
		t.setAlignment(Rectangle.ALIGN_LEFT);
		doc.add(t);
		
		//Registro de Modificaciones
		t = new Paragraph(" ");
		t.setAlignment(Rectangle.ALIGN_LEFT);
		doc.add(t);		
		
		
		doc.add(tablaRegistroModificacion());
		doc.add(tablaRegistroModificacion2());

		//Datos Generales
		t = new Paragraph("Datos Generales",new Font(Font.HELVETICA, 10f));
		t.setAlignment(Rectangle.ALIGN_LEFT);
		doc.add(t);
		
		//Espacio
		t = new Paragraph(" ");
		t.setAlignment(Rectangle.ALIGN_LEFT);
		doc.add(t);				
		
		doc.add(datosGenerales());
		
		for (Iterator iterator = listTablas.iterator(); iterator.hasNext();) {
			TablasDTO tablasDTO = (TablasDTO) iterator.next();

			
			//Nuevas Estructuras
			t = new Paragraph("Nuevas Estructuras",new Font(Font.HELVETICA, 10f));
			t.setAlignment(Rectangle.ALIGN_LEFT);
			doc.add(t);

			//Espacio
			t = new Paragraph(" ");
			t.setAlignment(Rectangle.ALIGN_LEFT);
			doc.add(t);				
			
			doc.add(tablaEstructura(tablasDTO));

			
			//Impresion de Campos y de tabla			
			doc.add(getDataTable(tablasDTO));
			
		}
		
    	doc.close();
	    docWriter.close();
		
	}


	private PdfPTable getDataTable(TablasDTO tablasDTO2) throws Throwable {
		
		List<CamposTablasDTO> camposTablas = this.getPresenterColumnas().consultarCamposPorIDTabla(tablasDTO2.getIdtabla());
		
		//definir estructura de la tabla
		PdfPTable datatable = new PdfPTable(7);
		//datatable.getDefaultCell().setPadding(3);
		int headerwidths[] = {5,30,20,20,40,20,20};//colWidth;
		datatable.setWidths(headerwidths);
		datatable.setHorizontalAlignment(Element.ALIGN_LEFT);
		datatable.setWidthPercentage(100F);

				
		PdfPCell c = null;
		String v = "";

		//Encabezados de Columnas
		//Id de Campo
		c = new PdfPCell( new Phrase("No.", tblHeaderFont) );
		c.setGrayFill(0.95f);
		c.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable.addCell(c);
		
		//Nombre de Campo
		c = new PdfPCell( new Phrase("Nombre de Campo", tblHeaderFont) );
		c.setGrayFill(0.95f);
		c.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable.addCell(c);
		
		//Tipo de Dato
		c = new PdfPCell( new Phrase("Tipo de Dato", tblHeaderFont) );
		c.setGrayFill(0.95f);
		c.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable.addCell(c);
		
		//Longitud
		c = new PdfPCell( new Phrase("Longitud", tblHeaderFont) );
		c.setGrayFill(0.95f);
		c.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable.addCell(c);
		
		//Descripcion
		c = new PdfPCell( new Phrase("Descripcion", tblHeaderFont) );
		c.setGrayFill(0.95f);
		c.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable.addCell(c);							

		//Miembro de Clave primaria
		c = new PdfPCell( new Phrase("Miembro de Clave primaria", tblHeaderFont) );
		c.setGrayFill(0.95f);
		c.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable.addCell(c);					

		//Miembro de Clave Secundaria
		c = new PdfPCell( new Phrase("Miembro de Clave Secundari", tblHeaderFont) );
		c.setGrayFill(0.95f);
		c.setHorizontalAlignment(Element.ALIGN_CENTER);
		datatable.addCell(c);
		
	
		//Impresion del Detalle de Campos
		for (Iterator iterator = camposTablas.iterator(); iterator.hasNext();) {
			CamposTablasDTO camposTablasDTO = (CamposTablasDTO) iterator.next();
			
			//Id de Campo
			c = new PdfPCell( new Phrase(String.valueOf(camposTablasDTO.getIdCampo()), tblBodyTableFont) );
			c.setHorizontalAlignment(Element.ALIGN_CENTER);
			datatable.addCell(c);
			
			//Nombre de Campo
			c = new PdfPCell( new Phrase(camposTablasDTO.getNameCampo(), tblBodyTableFont) );
			c.setHorizontalAlignment(Element.ALIGN_CENTER);
			datatable.addCell(c);
			
			//Tipo de Dato
			c = new PdfPCell( new Phrase(camposTablasDTO.getTipoDato(), tblBodyTableFont) );
			c.setHorizontalAlignment(Element.ALIGN_CENTER);
			datatable.addCell(c);
			
			//Longitud
			c = new PdfPCell( new Phrase(String.valueOf(camposTablasDTO.getLongCampo()), tblBodyTableFont) );
			c.setHorizontalAlignment(Element.ALIGN_CENTER);
			datatable.addCell(c);
			
			//Descripcion
			c = new PdfPCell( new Phrase(camposTablasDTO.getDescripcionCampo(), tblBodyTableFont) );
			c.setHorizontalAlignment(Element.ALIGN_CENTER);
			datatable.addCell(c);							

			//Miembro de Clave primaria
			c = new PdfPCell( new Phrase(camposTablasDTO.getPermiteNulo(), tblBodyTableFont) );
			c.setHorizontalAlignment(Element.ALIGN_CENTER);
			datatable.addCell(c);					

			//Miembro de Clave Secundaria
			c = new PdfPCell( new Phrase(camposTablasDTO.getPermiteNulo(), tblBodyTableFont) );
			c.setHorizontalAlignment(Element.ALIGN_CENTER);
			datatable.addCell(c);
			
			
		}
		
		return datatable;
	}

	@Override
	public InputStream getStream() {
		// TODO Auto-generated method stub
		return new ByteArrayInputStream(buf.toByteArray());
	}

	/**
	 * @return the tablasDTO
	 */
	public TablasDTO getTablasDTO() {
		return tablasDTO;
	}

	/**
	 * @param tablasDTO the tablasDTO to set
	 */
	public void setTablasDTO(TablasDTO tablasDTO) {
		this.tablasDTO = tablasDTO;
	}

	/**
	 * @return the presenterColumnas
	 */
	public ColumnasTablasPresenter getPresenterColumnas() {
		return presenterColumnas;
	}

	/**
	 * @param presenterColumnas the presenterColumnas to set
	 */
	public void setPresenterColumnas(ColumnasTablasPresenter presenterColumnas) {
		this.presenterColumnas = presenterColumnas;
	}

    public PdfPTable cabeceraDiccionario() throws Throwable{

		//definir estructura de la tabla
		PdfPTable cabeceraTable = new PdfPTable(2);
		cabeceraTable.getDefaultCell().setPadding(3);
		int headerwidths[] = {20,60};//colWidth;
		cabeceraTable.setWidths(headerwidths);
		cabeceraTable.setHorizontalAlignment(Element.ALIGN_LEFT);
		cabeceraTable.setWidthPercentage(100F);
		
		
		PdfPCell c = null;
		String v = "";

		//Encabezados de Columnas
		//Logo
		//c = new PdfPCell( new Phrase("Logo del Banco", tblHeaderFont) );
		//c.setGrayFill(0.95f);
		//c.setHorizontalAlignment(Element.ALIGN_CENTER);
		cabeceraTable.addCell(img);
		
		//Nombre de Campo
		c = new PdfPCell( new Phrase("DICCIONARIO DE DATOS", tblHeaderFont) );
		c.setGrayFill(0.95f);
		c.setHorizontalAlignment(Element.ALIGN_CENTER);
		cabeceraTable.addCell(c);

		return cabeceraTable;
    	
    }
    
    public Image logo(){
    	
		return img;
    	
    }
    
    public PdfPTable cabecera2() throws Throwable{
    	
		//definir estructura de la tabla
		PdfPTable cabeceraTable = new PdfPTable(1);
		cabeceraTable.getDefaultCell().setPadding(3);
		int headerwidths[] = {100};//colWidth;
		cabeceraTable.setWidths(headerwidths);
		cabeceraTable.setHorizontalAlignment(Element.ALIGN_LEFT);
		cabeceraTable.setWidthPercentage(100F);		
		
		PdfPCell c = null;
		String v = "";

		//Encabezados de Columnas
		c = new PdfPCell( new Phrase("1. TABLAS", tblHeaderFont) );
		c.setGrayFill(0.95f);
		c.setHorizontalAlignment(Element.ALIGN_CENTER);
		cabeceraTable.addCell(c);
		

		return cabeceraTable;    	
    }

	private PdfPTable tablaRegistroModificacion() throws Throwable {
		//definir estructura de la tabla
		
		PdfPTable cabeceraTable = new PdfPTable(3);
		cabeceraTable.getDefaultCell().setPadding(3);
		int headerwidths[] = {30,30,30};//colWidth;
		cabeceraTable.setWidths(headerwidths);
		cabeceraTable.setHorizontalAlignment(Element.ALIGN_LEFT);
		cabeceraTable.setWidthPercentage(100F);		
		
		PdfPCell c = null;
		String v = "";

		//Encabezados de Columnas
		c = new PdfPCell( new Phrase("Cod. **", tblHeaderFont) );
		c.setGrayFill(0.95f);
		c.setHorizontalAlignment(Element.ALIGN_LEFT);
		cabeceraTable.addCell(c);
		
		c = new PdfPCell( new Phrase("Proyecto", tblHeaderFont) );
		c.setGrayFill(0.95f);
		c.setHorizontalAlignment(Element.ALIGN_LEFT);
		cabeceraTable.addCell(c);
		
		c = new PdfPCell( new Phrase("Objetivo", tblHeaderFont) );
		c.setGrayFill(0.95f);
		c.setHorizontalAlignment(Element.ALIGN_LEFT);
		cabeceraTable.addCell(c);
		
        //Celdas en blanco
		//Encabezados de Columnas
		c = new PdfPCell( new Phrase(" ", tblHeaderFont) );
		c.setHorizontalAlignment(Element.ALIGN_LEFT);
		cabeceraTable.addCell(c);
		
		c = new PdfPCell( new Phrase(" ", tblHeaderFont) );
		c.setHorizontalAlignment(Element.ALIGN_LEFT);
		cabeceraTable.addCell(c);
		
		c = new PdfPCell( new Phrase(" ", tblHeaderFont) );
		c.setHorizontalAlignment(Element.ALIGN_LEFT);
		cabeceraTable.addCell(c);		
		

		c = new PdfPCell( new Phrase("Alcance", tblHeaderFont) );
		c.setGrayFill(0.95f);
		c.setHorizontalAlignment(Element.ALIGN_LEFT);
		c.setColspan(2);
		cabeceraTable.addCell(c);
		
		c = new PdfPCell( new Phrase("Beneficios", tblHeaderFont) );
		c.setGrayFill(0.95f);
		c.setHorizontalAlignment(Element.ALIGN_LEFT);
		c.setColspan(1);
		cabeceraTable.addCell(c);
		

		c = new PdfPCell( new Phrase(" ", tblHeaderFont) );
		c.setHorizontalAlignment(Element.ALIGN_LEFT);
		c.setColspan(2);
		cabeceraTable.addCell(c);
		
		c = new PdfPCell( new Phrase(" ", tblHeaderFont) );
		c.setHorizontalAlignment(Element.ALIGN_LEFT);
		c.setColspan(1);
		cabeceraTable.addCell(c);			


		return cabeceraTable;    	
	}
	
	private PdfPTable tablaRegistroModificacion2() throws Throwable {
		//definir estructura de la tabla
		
		PdfPTable cabeceraTable = new PdfPTable(5);
		cabeceraTable.getDefaultCell().setPadding(3);
		int headerwidths[] = {30,30,30,30,30};//colWidth;
		cabeceraTable.setWidths(headerwidths);
		cabeceraTable.setHorizontalAlignment(Element.ALIGN_LEFT);
		cabeceraTable.setWidthPercentage(100F);		
		
		PdfPCell c = null;
		String v = "";

		//Encabezados de Columnas
		c = new PdfPCell( new Phrase("Fecha", tblHeaderFont) );
		c.setGrayFill(0.95f);
		c.setHorizontalAlignment(Element.ALIGN_LEFT);
		cabeceraTable.addCell(c);
		
		c = new PdfPCell( new Phrase("Lider Tecnico", tblHeaderFont) );
		c.setGrayFill(0.95f);
		c.setHorizontalAlignment(Element.ALIGN_LEFT);
		cabeceraTable.addCell(c);
		
		c = new PdfPCell( new Phrase("Lider de Arquitectura IT", tblHeaderFont) );
		c.setGrayFill(0.95f);
		c.setHorizontalAlignment(Element.ALIGN_LEFT);
		cabeceraTable.addCell(c);

		c = new PdfPCell( new Phrase("Lider de Seguridad", tblHeaderFont) );
		c.setGrayFill(0.95f);
		c.setHorizontalAlignment(Element.ALIGN_LEFT);
		cabeceraTable.addCell(c);
		
		c = new PdfPCell( new Phrase("Gerente de Proyecto", tblHeaderFont) );
		c.setGrayFill(0.95f);
		c.setHorizontalAlignment(Element.ALIGN_LEFT);
		cabeceraTable.addCell(c);
		
       //Celdas en Blanco
		c = new PdfPCell( new Phrase(" ", tblHeaderFont) );
		c.setHorizontalAlignment(Element.ALIGN_LEFT);
		cabeceraTable.addCell(c);
		
		c = new PdfPCell( new Phrase(" ", tblHeaderFont) );
		c.setHorizontalAlignment(Element.ALIGN_LEFT);
		cabeceraTable.addCell(c);
		
		c = new PdfPCell( new Phrase(" ", tblHeaderFont) );
		c.setHorizontalAlignment(Element.ALIGN_LEFT);
		cabeceraTable.addCell(c);

		c = new PdfPCell( new Phrase(" ", tblHeaderFont) );
		c.setHorizontalAlignment(Element.ALIGN_LEFT);
		cabeceraTable.addCell(c);
		
		c = new PdfPCell( new Phrase(" ", tblHeaderFont) );
		c.setHorizontalAlignment(Element.ALIGN_LEFT);
		cabeceraTable.addCell(c);		
		


		return cabeceraTable;    	
	}
	
	private PdfPTable datosGenerales() throws Throwable {
		//definir estructura de la tabla
		
		PdfPTable cabeceraTable = new PdfPTable(3);
		cabeceraTable.getDefaultCell().setPadding(3);
		int headerwidths[] = {30,60,5};//colWidth;
		cabeceraTable.setWidths(headerwidths);
		cabeceraTable.setHorizontalAlignment(Element.ALIGN_LEFT);
		cabeceraTable.setWidthPercentage(100F);		
		
		PdfPCell c = null;
		String v = "";

		//Encabezados de Columnas
		c = new PdfPCell( new Phrase("Nombre de la Base de Datos", tblHeaderFont) );
		c.setGrayFill(0.95f);
		c.setHorizontalAlignment(Element.ALIGN_LEFT);
		cabeceraTable.addCell(c);
		
		c = new PdfPCell( new Phrase(" ", tblHeaderFont) );
		c.setHorizontalAlignment(Element.ALIGN_LEFT);
		cabeceraTable.addCell(c);
		
		c = new PdfPCell( new Phrase("Cod.", tblHeaderFont) );
		c.setGrayFill(0.95f);
		c.setHorizontalAlignment(Element.ALIGN_LEFT);
		cabeceraTable.addCell(c);

		c = new PdfPCell( new Phrase("Tamano Inicial aproximado", tblHeaderFont) );
		c.setGrayFill(0.95f);
		c.setHorizontalAlignment(Element.ALIGN_LEFT);
		cabeceraTable.addCell(c);
		
		c = new PdfPCell( new Phrase(" ", tblHeaderFont) );
		c.setHorizontalAlignment(Element.ALIGN_LEFT);
		cabeceraTable.addCell(c);

		c = new PdfPCell( new Phrase("Cod.", tblHeaderFont) );
		c.setGrayFill(0.95f);
		c.setHorizontalAlignment(Element.ALIGN_LEFT);
		cabeceraTable.addCell(c);
		
		c = new PdfPCell( new Phrase("Incremento de Tamano en el Primer Mes", tblHeaderFont) );
		c.setGrayFill(0.95f);
		c.setHorizontalAlignment(Element.ALIGN_LEFT);
		cabeceraTable.addCell(c);
		
		c = new PdfPCell( new Phrase(" ", tblHeaderFont) );
		c.setHorizontalAlignment(Element.ALIGN_LEFT);
		cabeceraTable.addCell(c);

		c = new PdfPCell( new Phrase("Cod.", tblHeaderFont) );
		c.setGrayFill(0.95f);
		c.setHorizontalAlignment(Element.ALIGN_LEFT);
		cabeceraTable.addCell(c);

		c = new PdfPCell( new Phrase("Incremento de Tamano en el Primer Ano", tblHeaderFont) );
		c.setGrayFill(0.95f);
		c.setHorizontalAlignment(Element.ALIGN_LEFT);
		cabeceraTable.addCell(c);
		
		c = new PdfPCell( new Phrase(" ", tblHeaderFont) );
		c.setHorizontalAlignment(Element.ALIGN_LEFT);
		cabeceraTable.addCell(c);

		c = new PdfPCell( new Phrase("Cod.", tblHeaderFont) );
		c.setGrayFill(0.95f);
		c.setHorizontalAlignment(Element.ALIGN_LEFT);
		cabeceraTable.addCell(c);
		
		c = new PdfPCell( new Phrase("Tiempo establecido de permanencia en linea", tblHeaderFont) );
		c.setGrayFill(0.95f);
		c.setHorizontalAlignment(Element.ALIGN_LEFT);
		cabeceraTable.addCell(c);
		
		c = new PdfPCell( new Phrase(" ", tblHeaderFont) );
		c.setHorizontalAlignment(Element.ALIGN_LEFT);
		cabeceraTable.addCell(c);

		c = new PdfPCell( new Phrase("Cod.", tblHeaderFont) );
		c.setGrayFill(0.95f);
		c.setHorizontalAlignment(Element.ALIGN_LEFT);
		cabeceraTable.addCell(c);
		
		c = new PdfPCell( new Phrase("Tiempo establecido de permanencia en Cinta", tblHeaderFont) );
		c.setGrayFill(0.95f);
		c.setHorizontalAlignment(Element.ALIGN_LEFT);
		cabeceraTable.addCell(c);
		
		c = new PdfPCell( new Phrase(" ", tblHeaderFont) );
		c.setHorizontalAlignment(Element.ALIGN_LEFT);
		cabeceraTable.addCell(c);

		c = new PdfPCell( new Phrase("Cod.", tblHeaderFont) );
		c.setGrayFill(0.95f);
		c.setHorizontalAlignment(Element.ALIGN_LEFT);
		cabeceraTable.addCell(c);


		return cabeceraTable;    	
	}
	
	private PdfPTable tablaEstructura(TablasDTO tablasDTO) throws Throwable {
		PdfPTable cabeceraTable = new PdfPTable(2);
		cabeceraTable.getDefaultCell().setPadding(3);
		int headerwidths[] = {90,5};//colWidth;
		cabeceraTable.setWidths(headerwidths);
		cabeceraTable.setHorizontalAlignment(Element.ALIGN_LEFT);
		cabeceraTable.setWidthPercentage(100F);		
		
		PdfPCell c = null;
		String v = "";

		//Encabezados de Columnas
		c = new PdfPCell( new Phrase("Nombre de la Tabla:"  + tablasDTO.getNombreTabla(), tblHeaderTableFont) );
		c.setGrayFill(0.95f);
		c.setHorizontalAlignment(Element.ALIGN_LEFT);
		cabeceraTable.addCell(c);
		
		c = new PdfPCell( new Phrase("Cod.", tblHeaderTableFont) );
		c.setGrayFill(0.95f);
		c.setHorizontalAlignment(Element.ALIGN_LEFT);
		cabeceraTable.addCell(c);
		
		c = new PdfPCell( new Phrase("Descripcion: " + tablasDTO.getDescripcion() , tblHeaderTableFont) );
		c.setGrayFill(0.95f);
		c.setHorizontalAlignment(Element.ALIGN_LEFT);
		cabeceraTable.addCell(c);
		
		c = new PdfPCell( new Phrase("Cod.", tblHeaderTableFont) );
		c.setGrayFill(0.95f);
		c.setHorizontalAlignment(Element.ALIGN_LEFT);
		cabeceraTable.addCell(c);		
		
		c = new PdfPCell( new Phrase("Tamano aproximado:", tblHeaderTableFont) );
		c.setGrayFill(0.95f);
		c.setHorizontalAlignment(Element.ALIGN_LEFT);
		cabeceraTable.addCell(c);
		
		c = new PdfPCell( new Phrase("Cod.", tblHeaderTableFont) );
		c.setGrayFill(0.95f);
		c.setHorizontalAlignment(Element.ALIGN_LEFT);
		cabeceraTable.addCell(c);
		
		c = new PdfPCell( new Phrase("Tipo: " + tablasDTO.getTipoTabla(), tblHeaderTableFont) );
		c.setGrayFill(0.95f);
		c.setHorizontalAlignment(Element.ALIGN_LEFT);
		cabeceraTable.addCell(c);
		
		c = new PdfPCell( new Phrase("Cod.", tblHeaderTableFont) );
		c.setGrayFill(0.95f);
		c.setHorizontalAlignment(Element.ALIGN_LEFT);
		cabeceraTable.addCell(c);		
		

		c = new PdfPCell( new Phrase("Tabla con data sensible: " + tablasDTO.getDataSensible(), tblHeaderTableFont) );
		c.setGrayFill(0.95f);
		c.setHorizontalAlignment(Element.ALIGN_LEFT);
		cabeceraTable.addCell(c);
		
		c = new PdfPCell( new Phrase("Cod.", tblHeaderTableFont) );
		c.setGrayFill(0.95f);
		c.setHorizontalAlignment(Element.ALIGN_LEFT);
		cabeceraTable.addCell(c);
		
		c = new PdfPCell( new Phrase("Frecuencia de Depuracion: " + tablasDTO.getFrecuenciaRespaldo(), tblHeaderTableFont) );
		c.setGrayFill(0.95f);
		c.setHorizontalAlignment(Element.ALIGN_LEFT);
		cabeceraTable.addCell(c);
		
		c = new PdfPCell( new Phrase("Cod.", tblHeaderTableFont) );
		c.setGrayFill(0.95f);
		c.setHorizontalAlignment(Element.ALIGN_LEFT);
		cabeceraTable.addCell(c);		

		c = new PdfPCell( new Phrase("Tipo de Particion de la tabla: " +  tablasDTO.getTipoParticion(), tblHeaderTableFont) );
		c.setGrayFill(0.95f);
		c.setHorizontalAlignment(Element.ALIGN_LEFT);
		cabeceraTable.addCell(c);
		
		c = new PdfPCell( new Phrase("Cod.", tblHeaderTableFont) );
		c.setGrayFill(0.95f);
		c.setHorizontalAlignment(Element.ALIGN_LEFT);
		cabeceraTable.addCell(c);		


		return cabeceraTable;
	}

	
}
