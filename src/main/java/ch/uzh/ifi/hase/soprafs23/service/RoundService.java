package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.TriviaAPIHandler.APIOutput;
import ch.uzh.ifi.hase.soprafs23.config.SocketService;
import ch.uzh.ifi.hase.soprafs23.constant.Categories;
import ch.uzh.ifi.hase.soprafs23.entity.Lobby;
import ch.uzh.ifi.hase.soprafs23.entity.Round;
import ch.uzh.ifi.hase.soprafs23.repository.LobbyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class RoundService {
    private final UserService userService;
    private final LobbyRepository lobbyRepository;
    private final LobbyService lobbyService;
    private final SocketService socketService;

    @Autowired
    public RoundService(UserService userService, @Qualifier("lobbyRepository") LobbyRepository lobbyRepository,
                        SocketService socketService, LobbyService lobbyService) {
        this.userService = userService;
        this.lobbyRepository = lobbyRepository;
        this.lobbyService = lobbyService;
        this.socketService = socketService;
    }

    /**
     *
     *  all round-related methods
     * */
    public Round getRound(Long lobbyId) {
        return lobbyService.getLobby(lobbyId).getRound();
    }


    public void createRound(Long id) {
        Lobby lobby = lobbyService.getLobby(id);
        Round round = new Round();

        //map round and lobby
        lobby.setRound(round);
        round.setLobby(lobby);

        //fetch 4 random questions
        round.setCategories(generateCategories());

        //set round id since it is a primary key
        round.setId(id);

        lobbyRepository.save(lobby);

    }

    private List<Categories> generateCategories() {
        //get Enums in an Array, shuffle it, get the first 4
        List<Categories> enumList = new ArrayList<Categories>(Arrays.asList(Categories.values()));
        Collections.shuffle(enumList);
        List<Categories> randomEnums = enumList.subList(0, 4);
        return randomEnums;
    }


    public void chooseCategory(Long lobbyId) {
        Round round = getRound(lobbyId);


        //fetch Array with Category votes
        List<Categories> categoryVotes = round.getCategoryVotes();
        List<Categories> categories = round.getCategories();

            //Call helper method to find which category got the most votes
            Categories chosenCategory = getCategoryWithMostVotes(categories, categoryVotes);
            System.out.println(chosenCategory + "came back from method");
            round.setChosenCategory(chosenCategory);


        lobbyService.getLobby(lobbyId).getRound().setTimerOver(false);

    }

    private Categories getCategoryWithMostVotes(List<Categories> categories, List<Categories> categoryVotes){
        Random random = new Random();

        //setup Hash Map with empty values
        Map<Categories, Integer> voteCounts = new HashMap<>();
        for (Categories category : categories) {
            voteCounts.put(category, 0);
        }

        // Count the votes for each category
        for (Categories vote : categoryVotes) {
            voteCounts.put(vote, voteCounts.get(vote) + 1);
        }

        // Find the category with the most votes
        Categories mostVotedCategory = null;
        //init with -1 in case no votes were taken
        int maxVotes = -1;
        for (Map.Entry<Categories, Integer> entry : voteCounts.entrySet()) {
            if (entry.getValue() > maxVotes) {
                maxVotes = entry.getValue();
                mostVotedCategory = entry.getKey();
                System.out.println(entry.getKey() + " shall be played");
            }
            //if two categories have the same number votes there is a 50/50 chance which category is going to be taken
            else if (entry.getValue() == maxVotes) {
                if(random.nextInt(2) == 1){
                    mostVotedCategory = entry.getKey();
                    System.out.println(entry.getKey() + " shall be played instead");
                }
            }
        }
        return mostVotedCategory;
    }

    public void incVoteCount(Long lobbyId){
        getRound(lobbyId).incrementAnswerCount();
    }


    public void setAPIOutput(Long lobbyId, APIOutput question){

        //fetch lobby and round
        Lobby lobby = lobbyService.getLobby(lobbyId);
        Round round = getRound(lobbyId);

        //get random index to insert the correct answer at a random place into all Answers
        Random rand = new Random();
        int randIndex = rand.nextInt(4);

        //set index of correct answer and the current questions
        round.setRightAnswer((long) randIndex);
        round.setCurrentQuestion(question.getApiOutputQuestion().getText());

        //add the correct answer to all answers at a random index and save it
        List<String> allAnswers = question.getIncorrectAnswers();
        allAnswers.add(randIndex, question.getCorrectAnswer());
        round.setAnswers(allAnswers);

        lobbyRepository.save(lobby);

    }

    public void addCategoryVote(Categories category, Long lobbyId) {
        Round round = getRound(lobbyId);
        round.addCategoryVotes(category);
        lobbyRepository.save(lobbyService.getLobby(lobbyId));
    }


}
