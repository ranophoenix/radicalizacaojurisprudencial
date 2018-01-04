<!DOCTYPE HTML>

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<title>Relatório</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
</head>

<body>
<div class="container-fluid">
	<section>		
			<article>
				<header>
					<h2>Sumário (${nome})</h2>
					<h3>Total de Queries: #{totalDeQueries}</h3>
				</header>
				<p>
				Query #ID: <input type="number" id="txtQID" size="3" min="1" max="#{totalDeQueries}" /><input type="button" onClick="var qid='#q' + $('#txtQID').val(); $(qid)[0].scrollIntoView();" value="Ir" />
				</p>
	            <table style="width:100%" class="table table-bordered">
	            <tr>
	            	<th>Algoritmo</th>
	            	<th>MAP</th>
	            	<th>MRP</th>
	            	<th>MPC(10)</th>
	            </tr>
	            <#list algoritmos?keys as key>
	            <tr>
	            	<td>${key}</td>
	            	<td>${algoritmos[key]['MAP'].all?string('0.000')}</td>
	            	<td>${algoritmos[key]['MRP'].all?string('0.000')}</td>
	            	<td>${algoritmos[key]['MPC(10)'].all?string('0.000')}</td>
	            </tr>
				</#list> 
	            </table>	
	            <p>
	            	MAP = Mean Average Precision<br/>
	            	MRP = Mean of R-Precision<br/>
	            	MPC(10) = Mean of Precision at Cut-off 10<br/>
	            </p>		
			</article>
		<#list relevantesPorConsulta?keys as qid>
		<article>
			<header>
	            <h3 id="q#{qid}"><a href="q#{qid}">Query #{qid}</a></h3>
	            <p>Texto: ${.vars['q'+ qid]}</p>
            	<#list algoritmos?keys as key>
            	<p>${key} : ${Util.aplicarAlgoritmo(key,.vars['q'+ qid])} </p>
				</#list> 	            
    	        <h4 class="text-center">Documentos relevantes</h4>
			</header>
			<div class="row">
				<div class="col-md-4"></div>
				<div class="col-md-4">
		            <table style="width:100%" class="table table-bordered">
		              <tr>
		                <th>DOC #ID</th>
		              </tr>
		              <#list relevantesPorConsulta?api.get(qid) as documento>
		              <tr>
		                <td>${documento.docno}</td>
		              </tr>
		              </#list>
		              <tr>
		              	<#assign totalDeRelevantes = relevantesPorConsulta?api.get(qid)?size>
		              	<td><strong>Total: #{totalDeRelevantes}</strong></td>
		              </tr>
		            </table>             
		         </div> 
		        <div class="col-md-4"></div>          
		</article>		
		<article>
			<header>
				<h4 class="text-center">Relevantes por Algoritmo</h4>
			</header>
			    <div class="row">
            	<#list algoritmos?keys as key>
            	<#assign resultados = algoritmos[key]['ROWS']>
            	<#assign contador = 0>
            	<#if key == "NoStem">
            		<div class="col-md-2 col-md-offset-1">
            	<#else>
            		<div class="col-md-2">
            	</#if>
                    <table style="width:100%" class="table table-bordered">
                      <tr>
                        <th colspan="2">${key} (N: ${resultados?api.get(qid)?size} , AP:  ${algoritmos[key]['MAP'].get(qid)?string('0.000')}, RP: ${algoritmos[key]['MRP'].get(qid)?string('0.000')}, PC(10): ${algoritmos[key]['MPC(10)'].get(qid)?string('0.000')})</th>                        
                      </tr>
                      <tr>
                        <th>Posição</th>
                        <th>DOC #ID</th>
                      </tr>                      
                      <#list resultados?api.get(qid) as resultado>                      
                      <#if contador < totalDeRelevantes && Util.isRelevant(relevantes, resultado)>
                      <#assign contador = contador + 1>              
                      <tr>
                        <td>#{resultado?counter}</td>
                        <td>${resultado.docno}</td>
                      </tr>
                      </#if>
                      </#list>
                    </table>                          
                 </div>              
                </#list>
                </div>
            <p>
            	N = Número de resultados retornados pela busca<br/>
            	AP = Average Precision<br/>
            	RP = R-Precision<br/>
            	PC(10) = Precision at Cut-off 10<br/>
            </p>		
            
		</article>
		</#list>
	</section>
	</div>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</body>

</html>