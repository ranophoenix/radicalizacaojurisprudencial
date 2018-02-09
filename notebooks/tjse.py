import pandas as pd

def carregar_dados_experimento1():
    sg_a = pd.read_csv('../csv/SegundoGrauAcordaos.csv')
    sg_a['Coleção'] = 'Segundo Grau - Acórdãos'
    sg_dm = pd.read_csv('../csv/SegundoGrauDecisoesMonocraticas.csv')
    sg_dm['Coleção'] = 'Segundo Grau - Decisões Monocráticas'
    tr_a = pd.read_csv('../csv/TurmaRecursalAcordaos.csv')
    tr_a['Coleção'] = 'Turma Recursal - Acórdãos'
    tr_dm = pd.read_csv('../csv/TurmaRecursalDecisoesMonocraticas.csv')
    tr_dm['Coleção'] = 'Turma Recursal - Decisões Monocráticas'

    corpora = pd.concat([sg_a, sg_dm, tr_a, tr_dm])
    corpora.sample(5)

    def mudar_rslps(col):
            if col == 'RSLPS':
                return 'RSLP-S'
            else:
                return col

    corpora['Stemmer'] = corpora['Stemmer'].apply(mudar_rslps)
    
    return corpora