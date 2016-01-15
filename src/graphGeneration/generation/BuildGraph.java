package graphGeneration.generation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;
import com.tinkerpop.blueprints.util.io.graphml.GraphMLTokens;
import com.tinkerpop.blueprints.util.io.graphml.GraphMLWriter;
import graphGeneration.analyse.FileAnalysisResult;
import graphGeneration.analyse.PdfTextAnalysis;
import graphGeneration.generation.Article;
import graphGeneration.generation.GenGraphs;

public class BuildGraph {
	Graph keywordsGraph = new TinkerGraph();
	final static String keywordsGraph_Prefix = "_KW";
	Graph saisonGraph = new TinkerGraph();
	final static String saisonGraph_Prefix = "_SAISON";
	Graph auteurGraph = new TinkerGraph();
	final static String auteurGraph_Prefix = "_AUTEUR";
	final int scoreMinKW = 1;

	public BuildGraph(String filepathsmane){
		int nbe = 0;
		System.out.println("BuildGraph number of files: " + PdfTextAnalysis.getResults().size());

		//Building graph based on keywords
		for (FileAnalysisResult r1 : PdfTextAnalysis.getResults()){
			Vertex node1 = getKeywordsGraph().addVertex(r1);
			Vertex node2 = getSaisonGraph().addVertex(r1);
			Vertex node3 = getAuteurGraph().addVertex(r1);
			String na = r1.getFilePath();
			String[] ns = na.split("/");
			Article art  = GenGraphs.getAllEntries().searchArticle(na);
			String image="";
			String titre="";
			String auteur="";
			String url="";
			String urlSaison="";
			String filename ="";
			if (art != null){
				image = art.getImage();
				titre = art.getTitre().replace('_',' ');
				url = art.getURL();
				urlSaison = art.getUrlSaison();
				filename = art.getFilename();
				for (String t: art.getKeywords()){
					String[] ns1 = t.split("#");
					if ("".equals(auteur)){
						auteur = ns1[ns1.length - 1].replace('_',' ');
					} else {
						auteur = auteur + ", " + ns1[ns1.length - 1].replace('_',' ');
					}
				}
			}
			node1.setProperty("name", ns[ns.length - 1]);
			node1.setProperty("image", image);
			node1.setProperty("titre", titre);
			node1.setProperty("auteur", auteur);
			node1.setProperty("url", url);
			node1.setProperty("saison", urlSaison);
			node1.setProperty("filename", filename);

			node2.setProperty("name", ns[ns.length - 1]);
			node2.setProperty("image", image);
			node2.setProperty("titre", titre);
			node2.setProperty("auteur", auteur);
			node2.setProperty("url", url);
			node2.setProperty("saison", urlSaison);
			node2.setProperty("filename", filename);

			node3.setProperty("name", ns[ns.length - 1]);
			node3.setProperty("image", image);
			node3.setProperty("titre", titre);
			node3.setProperty("auteur", auteur);
			node3.setProperty("url", url);
			node3.setProperty("saison", urlSaison);
			node3.setProperty("filename", filename);

			System.out.println("Adding node: " + ns[ns.length - 1] + " " + image);
		}
		nbe = 0;
		//Concept
		if (GenGraphs.gui){
			for (int i = 0; i< PdfTextAnalysis.getResults().size(); i++){
				FileAnalysisResult r1 = PdfTextAnalysis.getResult(i);
				for (int j = i+1; j< PdfTextAnalysis.getResults().size(); j++){
					FileAnalysisResult r2 = PdfTextAnalysis.getResult(j);
					int score = r1.countProximity(r2) ;
					if (score >= scoreMinKW){
						Vertex n1 = getKeywordsGraph().getVertex(r1);
						Vertex n2 = getKeywordsGraph().getVertex(r2);
						Edge e1 = getKeywordsGraph().addEdge(null,n1, n2, Integer.toString(score));
						//Edge e2 = getKeywordsGraph().addEdge(null,n2, n1, Integer.toString(score));
						System.out.println("Adding edge between: " + n1.getProperty("name")+ " --> " +n2.getProperty("name") + " val : " + score  );
						nbe++;
					}
				}
			}
			System.out.println("Concept number of edges added: "+ nbe);
		}
		//Auteur
		nbe = 0;
		for (int i = 0; i< PdfTextAnalysis.getResults().size(); i++){
			FileAnalysisResult r1 = PdfTextAnalysis.getResult(i);
			if (r1 == null) continue;
			for (int j = i+1; j< PdfTextAnalysis.getResults().size(); j++){
				FileAnalysisResult r2 = PdfTextAnalysis.getResult(j);
				if (r2 == null) continue;
				int score = r1.getArticleRes().countProximity(r2.getArticleRes()) ;
				if (score !=0){
					Vertex n1 = getAuteurGraph().getVertex(r1);
					Vertex n2 = getAuteurGraph().getVertex(r2);
					Edge e1 = getAuteurGraph().addEdge(null,n1, n2, Integer.toString(score));
					//Edge e2 = getAuteurGraph().addEdge(null,n2, n1, Integer.toString(score));
					//System.out.println("Adding edge between: " + n1.getProperty("name")+ " --> " +n2.getProperty("name") + " val : " + score  );
					nbe++;
				}
			}
		}
		System.out.println("Auteur number of edges added: "+ nbe);
		//Saison
		nbe = 0;
		for (int i = 0; i< PdfTextAnalysis.getResults().size(); i++){
			FileAnalysisResult r1 = PdfTextAnalysis.getResult(i);
			for (int j = i+1; j< PdfTextAnalysis.getResults().size(); j++){
				FileAnalysisResult r2 = PdfTextAnalysis.getResult(j);
				if (r1.getArticleRes().getSaison().equals(r2.getArticleRes().getSaison())){
					Vertex n1 = getSaisonGraph().getVertex(r1);
					Vertex n2 = getSaisonGraph().getVertex(r2);
					Edge e1 = getSaisonGraph().addEdge(null,n1, n2, "");
					//Edge e2 = getSaisonGraph().addEdge(null,n2, n1, "");
					//System.out.println("Adding edge between: " + n1.getProperty("name")+ " --> " +n2.getProperty("name"));
					nbe++;
				}
			}
		}
		System.out.println("Saison number of edges added: "+ nbe);
		String nname =  filepathsmane.replaceAll(".graphml", keywordsGraph_Prefix+".graphml");
		try {
			writeXMLGraph(getKeywordsGraph(),nname);
			System.out.println("Written graph file: "+nname);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		nname =  filepathsmane.replaceAll(".graphml", saisonGraph_Prefix+".graphml");
		try {
			writeXMLGraph(getSaisonGraph(),nname);
			System.out.println("Written graph file: "+nname);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		nname =  filepathsmane.replaceAll(".graphml", auteurGraph_Prefix+".graphml");
		try {
			writeXMLGraph(getAuteurGraph(),nname);
			System.out.println("Written graph file: "+nname);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public static void writeXMLGraph(Graph g, String filepathsmane) throws IOException{
		OutputStream so = null;
		File f = new File(filepathsmane);
		so = new FileOutputStream(f);
		GraphMLWriter writer = new GraphMLWriter(g);
		// used only to speedup output...
		Map<String, String> vertexKeyTypes = new HashMap<String, String>();
		vertexKeyTypes.put("name", GraphMLTokens.STRING);
		writer.setVertexKeyTypes(vertexKeyTypes);
		//write the graph...
		GraphMLWriter.outputGraph(g, so);
		so.close();
	}

	public Graph getKeywordsGraph() {
		return keywordsGraph;
	}

	public Graph getSaisonGraph() {
		return saisonGraph;
	}

	public void setSaisonGraph(Graph saisonGraph) {
		this.saisonGraph = saisonGraph;
	}

	public Graph getAuteurGraph() {
		return auteurGraph;
	}

	public void setAuteurGraph(Graph auteurGraph) {
		this.auteurGraph = auteurGraph;
	}
}
