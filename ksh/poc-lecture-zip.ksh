#!/bin/ksh

#--------------------------------------------------------------------------------
# @(#)SCRIPT                    : poc-lecture-zip.ksh
#--------------------------------------------------------------------------------
# @(#)Application               : SGE-L
# @(#)Fonction                  : Lancement du batch poc-lecture-zip
# @(#)Version                   : 1.00
# @(#)Auteur                    : Capgemini
# @(#)Date de creation          : $currentDate
# @(#)Parametres d'entree       :
# @(#)Codes retour              : 
# @(#)Utilisateur               : webadm
# @(#)Ressources utilisees      :
# @(#)Etapes du script          :
# @(#)Modifications             :
#--------------------------------------------------------------------------------

# Deplacement dans le dossier shl
cd $(dirname $0)

###########################
# Declaration des variables
###########################

# Initialisation pour msglog
. ./msglog

############################
# Affectation des parametres
############################


############################
# Declaration des fonctions
############################

function init_vars
#---------------------------------------------------------------------#
# Fonction             : Initialisation des variables globales
# Parametres d'entree  : -
# Retour               : -
#---------------------------------------------------------------------#
{
        mkdir -p $LOGS_DIR"/logs"
        export BATCH_LOG_FILE="${LOGS_DIR}/logs/${BATCH_NAME}_$(date "+%Y%m%d-%H%M").log"
}

function trace
#------------------------------------------------------------------------------#
# Parametres d'entree  :                                                       #
#@(#) Parametres d'entree  : Log          Fichier recevant la trace
#@(#)                        Severite     0 Ok,1 Warning,3 Erreur
#@(#)                        Code_erreur  Numero d'erreur entre 0 et 9999
#@(#)                        Message      Libelle de l'erreur					#
# Parametres de sortie : Aucun                                                 #
# Retour               : 0 succes erreur                                       #
#------------------------------------------------------------------------------#
{
        msglog "$1" "$2" "$3" "$4" > /dev/null 2>&1
}

function info
{
        trace "$BATCH_LOG_FILE" "$INFO" "$2" "$1"
}

function error
{
        trace "$BATCH_LOG_FILE" "$ERROR" "$2" "$1"
}

function delete_semaphore
{
		#On retire le verrou
		if [[ -e $SEMAPHORE_FILE ]] ; then
			rm $SEMAPHORE_FILE
		fi
}

function exit_error
#---------------------------------------------------------------------#
# Fonction             : Test la dernière execution et retourne un eventuel code d'erreur en interrompant le programme
# Parametres d'entree  : (1) code retour de la dernière execution ($?)
#						 (2) code que le programme doit retourner en cas d'erreur (exit code)
#						 (3) message associé a mettre dans les logs
#						 (4) code d'erreur du message dans les logs
# Retour               : -
#---------------------------------------------------------------------#
{
		#delete semaphore before exiting
		delete_semaphore
		
		typeset -i EXIT_CODE_EVAL		
		EXIT_CODE_EVAL=$1
		EXIT_CODE_RETURN=$2
		EXIT_MESSAGE=$3
		EXIT_CODE_MESSAGE=$4
		
		if [ $EXIT_CODE_EVAL -ne 0  ] ; then
			error "$EXIT_MESSAGE" $EXIT_CODE_MESSAGE
			exit $EXIT_CODE_RETURN
		fi
}

###############################
# Debut du script
###############################

	#Chargement du fichier de configuration des variables d'environnement
	. ${PWD}/../conf/poc-lecture-zip-config-technique.properties 2>/dev/null

	#le message indiquant que le fichier de propriete est mal charge se trouve... dans le fichier de propriete
	exit_error $? 2 "$TRV_UCU06_ERR_001" "001"

	#Chargement du fichier de configuration des variables applicatives
	. ${PWD}/../conf/poc-lecture-zip-config-fonctionnel.properties 2>/dev/null

	exit_error $? 2 "$TRV_UCU06_ERR_001" "001"
	
	echo 
	echo "Starting [BATCH_NAME = $BATCH_NAME, USER = $BATCH_USER]..."
	echo
	
	#Controle de l'utilisateur executant le batch
	UTILISATEUR=`whoami`
    if [[ $UTILISATEUR != $BATCH_USER ]] ; then
    	echo ""
        echo "$TRV_UCU06_ERR_002"
        echo ""
        #delete semaphore before exiting
		delete_semaphore
        exit
	fi
	
	#initialisation des variables d'environnement locale
	init_vars

	info "$TRV_UCU06_INFO_001" "001"

	#controle si le batch n'a pas deja ete execute
	if [[ -e $SEMAPHORE_FILE ]] ; then
		echo ""
        echo "$TRV_UCU06_ERR_004 - batch is already running. check semaphore file"
        echo ""
		exit_error 1 2 "$TRV_UCU06_ERR_004" "004"
	fi

	#On pose le verrou
	touch $SEMAPHORE_FILE

	#lancement du batch
	echo "java -cp "$CLASSPATH_BATCH" -Dlog4j.configuration=file:../conf/log4j-batches.xml -Dlog.directory.spv=$LOG_DIRECTORY_SPV -Dweblogic.MaxMessageSize=20000000 -Dbatch.name=$BATCH_NAME -Dsgel.batch.config.path=$CONFIG_FOLDER org.springframework.batch.core.launch.support.CommandLineJobRunner applicationContext.xml $JOB_NAME curdate=$(date "+%Y%m%d%H%M")"
	echo .

	java -cp "$CLASSPATH_BATCH" -Dlog4j.configuration=file:../conf/log4j-batches.xml -Dlog.directory.spv=$LOG_DIRECTORY_SPV -Dweblogic.MaxMessageSize=20000000 -Dbatch.name=$BATCH_NAME -Dsgel.batch.config.path=$CONFIG_FOLDER org.springframework.batch.core.launch.support.CommandLineJobRunner applicationContext.xml $JOB_NAME curdate=$(date "+%Y%m%d%H%M")
	exit_code_batch=$?
	

	exit_error $exit_code_batch $exit_code_batch "$TRV_UCU06_ERR_003" "003"
	
	info "$TRV_UCU06_INFO_002" "001"
	
	#On retire le verrou
	delete_semaphore
	
	echo
	echo "Batch completed"
	echo
	
	exit 0

##############################
# Fin du script
##############################
