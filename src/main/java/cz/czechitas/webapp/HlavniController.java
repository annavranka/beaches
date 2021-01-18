package cz.czechitas.webapp;

import java.io.*;
import java.util.*;
import org.springframework.core.io.*;
import org.springframework.core.io.support.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

@Controller
public class HlavniController {

    private List<String> obrazky;
    private List<Boolean> rightAnswers;

    public HlavniController() throws IOException {
        ResourcePatternResolver prohledavacSlozek = new
                PathMatchingResourcePatternResolver();
        List<Resource> resources = Arrays.asList(
                prohledavacSlozek.getResources("classpath:/static/images/beaches/*")
        );
        obrazky = new ArrayList<>();
        for (Resource prvek : resources) {
            obrazky.add(prvek.getFilename());
        }

        rightAnswers = new ArrayList<>();
        for (String beaches : obrazky) {
            if (beaches.contains("png")) {
                rightAnswers.add(false);
            } else {
                rightAnswers.add(true);
            }
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView zobrazIndex() {
        ModelAndView drzakNaDataAJmenoSablony = new ModelAndView("index");
        drzakNaDataAJmenoSablony.addObject("beachList", obrazky);
        return drzakNaDataAJmenoSablony;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ModelAndView zpracujIndex(BeachForm vyplnenyFormular) {
        int score = 0;
        for (int i = 0; i < vyplnenyFormular.getAnswers().size(); i++) {
            boolean answer = vyplnenyFormular.getAnswers().get(i);
            boolean rightAnswer = rightAnswers.get(i);
            if (rightAnswer == answer) {
                score++;
            }
        }
        ModelAndView data = new ModelAndView("vysledek");
        data.addObject("score",score);
        return data;

    }

}
