package gestoreSchede.GUI.utility;

import gestoreSchede.oggetti.SchedaDescrittiva;
import gestoreSchede.oggetti.elementi.Dipendente;
import gestoreSchede.oggetti.elementi.Spazio;
import gestoreSchede.oggetti.elementi.Strumentazione;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/*******************
 * © APT Software *
 *******************/
public class CampoRicerca {

    public static void attivaCampoRicercaDipendenti(ObservableList<Dipendente> listaDipendenti,
                                                    TextField campoDiRicercaElementi,
                                                    TableView<Dipendente> tabellaDipendenti) {

        FilteredList<Dipendente> filteredDataDip = new FilteredList<>(listaDipendenti, p -> true);

        // Esegui quando viene inserito del testo nel campo di ricerca
        campoDiRicercaElementi.textProperty().addListener((observable, oldValue, newValue) ->
                filteredDataDip.setPredicate(dipendente -> {

                    // Se il campo di ricerca è vuoto, mostra l'intera lista
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    return dipendente.getNome().toLowerCase().contains(lowerCaseFilter) || // Nome Trovato
                            dipendente.getCognome().toLowerCase().contains(lowerCaseFilter) || // Cognome Trovato
                            dipendente.getMansione().toLowerCase().contains(lowerCaseFilter) || // Mansione Trovata
                            dipendente.getNumTelefono().toLowerCase().contains(lowerCaseFilter) || // N. Telefono Trovato
                            dipendente.getDataNascita().toString().toLowerCase().contains(lowerCaseFilter);// Data Nascita Trovata

                }));

        // Inserisci la lista filtrata in una sortedList
        SortedList<Dipendente> sortedData = new SortedList<>(filteredDataDip);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(tabellaDipendenti.comparatorProperty());

        // Mostra tutti i risultati nella tabella
        tabellaDipendenti.setItems(sortedData);

    }

    public static void attivaCampoRicercaStrumentazioni(ObservableList<Strumentazione> listaStrumentazioni,
                                                        TextField campoDiRicercaElementi,
                                                        TableView<Strumentazione> tabellaStrumentazioni) {

        FilteredList<Strumentazione> filteredDataStrumentazioni = new FilteredList<>(listaStrumentazioni, p -> true);

        // Esegui quando viene inserito del testo nel campo di ricerca
        campoDiRicercaElementi.textProperty().addListener((observable, oldValue, newValue) ->
                filteredDataStrumentazioni.setPredicate(strumentazione -> {

            // Se il campo di ricerca è vuoto, mostra l'intera lista
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }

            String lowerCaseFilter = newValue.toLowerCase();


            return strumentazione.getNome().toLowerCase().contains(lowerCaseFilter) || // Nome Trovato
                    strumentazione.getDescrizione().toLowerCase().contains(lowerCaseFilter); // Descrizione trovata
        }));

        // Inserisci la lista filtrata in una sortedList
        SortedList<Strumentazione> sortedData = new SortedList<>(filteredDataStrumentazioni);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(tabellaStrumentazioni.comparatorProperty());

        // Mostra tutti i risultati nella tabella
        tabellaStrumentazioni.setItems(sortedData);

    }

    public static void attivaCampoDiRicercaSpazi(ObservableList<Spazio> listaSpazi,
                                                 TextField campoDiRicercaElementi,
                                                 TableView<Spazio> tabellaSpazi){

        FilteredList<Spazio> filteredDataSpazi = new FilteredList<>(listaSpazi, p -> true);

        // Esegui quando viene inserito del testo nel campo di ricerca
        campoDiRicercaElementi.textProperty().addListener((observable, oldValue, newValue) ->
                filteredDataSpazi.setPredicate(spazio -> {

                    // Se il campo di ricerca è vuoto, mostra l'intera lista
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    return spazio.getAltreInformazioni().toLowerCase().contains(lowerCaseFilter) ||
                            spazio.getNome().toLowerCase().contains(lowerCaseFilter) ||
                            spazio.getUbicazione().toLowerCase().contains(lowerCaseFilter);

                }));

        // Inserisci la lista filtrata in una sortedList
        SortedList<Spazio> sortedData = new SortedList<>(filteredDataSpazi);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(tabellaSpazi.comparatorProperty());

        // Mostra tutti i risultati nella tabella
        tabellaSpazi.setItems(sortedData);
    }

    public static void attivaCampoDiRicercaSchede(ObservableList<SchedaDescrittiva> listaSchede,
                                                  TextField campoDiRicercaSchede,
                                                  TableView<SchedaDescrittiva> tabellaSchede){

        FilteredList<SchedaDescrittiva> filteredDataSchede = new FilteredList<>(listaSchede, p -> true);

        // Esegui quando viene inserito del testo nel campo di ricerca
        campoDiRicercaSchede.textProperty().addListener((observable, oldValue, newValue) ->
                filteredDataSchede.setPredicate(scheda -> {

                    // Se il campo di ricerca è vuoto, mostra l'intera lista
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    // Nome Trovato
                    return scheda.getNomeScheda().toLowerCase().contains(lowerCaseFilter);
                }));

        // Inserisci la lista filtrata in una sortedList
        SortedList<SchedaDescrittiva> sortedData = new SortedList<>(filteredDataSchede);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(tabellaSchede.comparatorProperty());

        // Mostra tutti i risultati nella tabella
        tabellaSchede.setItems(sortedData);
    }


}
