package com.sil.informatica.modules.sign;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.sil.informatica.modules.category.Category;
import com.sil.informatica.modules.category.CategoryRepository;

/// Seeder para popular o glossário com termos técnicos iniciais de informática em Libras.
@Component
public class SignSeeder implements CommandLineRunner {

    private final SignRepository signRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public SignSeeder(SignRepository signRepository, CategoryRepository categoryRepository) {
        this.signRepository = signRepository;
        this.categoryRepository = categoryRepository;
    }

    private Category getOrCreate(String name, Map<String, Category> cache) {
        return cache.computeIfAbsent(name, n -> 
            categoryRepository.findByNameIgnoreCase(n)
                .orElseGet(() -> categoryRepository.save(new Category(n)))
        );
    }

    private void saveOrUpdate(String term, String description, Category category, String videoUrl) {
        signRepository.findByTermIgnoreCase(term)
            .ifPresentOrElse(
                existing -> {
                    existing.setDescription(description);
                    existing.setCategory(category);
                    existing.setVideoUrl(videoUrl);
                    signRepository.save(existing);
                },
                () -> {
                    signRepository.save(new Sign(term, description, category, videoUrl));
                }
            );
    }

    @Override
    public void run(String... args) {
        Map<String, Category> categories = new HashMap<>();

        Category c1 = getOrCreate("Conceitos Fundamentais", categories);
        Category c2 = getOrCreate("Representação de Algoritmos", categories);
        Category c3 = getOrCreate("Estrutura Básica de um Programa", categories);
        Category c4 = getOrCreate("Variáveis e Tipos de Dados", categories);
        Category c5 = getOrCreate("Operadores e Expressões", categories);
        Category c6 = getOrCreate("Estruturas de Controle", categories);
        Category c7 = getOrCreate("Estruturas de Dados Básicas", categories);
        Category c9 = getOrCreate("Entrada e Saída de Dados", categories);
        Category c10 = getOrCreate("Conceitos Computacionais Complementares", categories);

        // 1. Conceitos Fundamentais
        saveOrUpdate("Lógica", "Organizar os pensamentos de forma clara para resolver um problema.", c1, "https://drive.google.com/file/d/1w8W5xjfiBKYaGLBnvC3V2DCONQLXnZOT/view?usp=drive_link");
        saveOrUpdate("Algoritmo", "Passo a passo organizado (como uma receita de bolo) para o computador resolver um problema.", c1, "https://drive.google.com/file/d/1NZuS6p4-CPnNTvaLIGky_X_EVP70acOv/view?usp=drive_link");
        saveOrUpdate("Abstração", "Olhar apenas para o que é mais importante em um problema e esquecer o resto (como desenhar uma pessoa usando apenas boneco de palito).", c1, "https://drive.google.com/file/d/1aHzgmd_Mzrn77hQJOozp4LEZZr2ElAwf/view?usp=drive_link");
        saveOrUpdate("Lógica de programação", "Jeito organizado de criar regras e ordens que o computador consegue entender.", c1, "https://drive.google.com/file/d/1MFeenEC9vr1fb8Tebs9AUKK3igwjuqgU/view?usp=drive_link");
        saveOrUpdate("Problema e solução", "Identificar uma necessidade (problema) e criar um programa para resolvê-la (solução).", c1, "https://drive.google.com/file/d/18PxlisthzIUa4IKbqB8t947eOoQDSxi1/view?usp=drive_link");
        saveOrUpdate("Sequência de passos", "Ordem correta de ações que devemos seguir para realizar uma tarefa.", c1, "https://drive.google.com/file/d/15xcH85h_C43nG2S8c28ZWC6V_9ZSOGv0/view?usp=drive_link");

        // 2. Representação de Algoritmos
        saveOrUpdate("Linguagem de programação", "A língua com regras e palavras que usamos para escrever instruções para o computador.", c2, "https://drive.google.com/file/d/1Mg65aLcrGe1hi07aP0M0vfRqkT14QwEU/view?usp=drive_link");
        saveOrUpdate("Descrição narrativa", "Escrever o passo a passo de um algoritmo usando a nossa própria língua falada (como o português escrito).", c2, "https://drive.google.com/file/d/1HhDZJbmTa3XqNdhWqphCkyZk-AE8-6gw/view?usp=drive_link");
        saveOrUpdate("Fluxograma", "Desenho ou mapa visual que usa formas geométricas para mostrar os passos de um algoritmo.", c2, "https://drive.google.com/file/d/105LR8kNjw0NK-IulOn4nET2pw_OQY3VB/view?usp=drive_link");
        saveOrUpdate("Portugol", "Linguagem em português muito parecida com código real, criada para ensinar programação de forma simples.", c2, "https://drive.google.com/file/d/1ut0N51A5ZJWF2FnKa2MnX9UtAvD7Ql8E/view?usp=drive_link");

        // 3. Estrutura Básica de um Programa
        saveOrUpdate("Entrada (input)", "Enviar dados ou comandos do mundo real para dentro do programa de computador.", c3, "https://drive.google.com/file/d/1iHqzWZTn9Gkgw4BcEUKphQ_PX6yQtP9E/view?usp=drive_link");
        saveOrUpdate("Processamento", "Quando o computador trabalha com os dados que recebeu para calcular, organizar ou transformar informações.", c3, "https://drive.google.com/file/d/1ze8fXxxDnjM5zMWQ0fWWWWa82p0e72hX/view?usp=drive_link");
        saveOrUpdate("Saída (output)", "O resultado (texto, som, imagem) que o computador exibe na tela para o usuário.", c3, "https://drive.google.com/file/d/1eAA0v-11vgHmwVrOfWWwv5B_0bxtUTLc/view?usp=drive_link");
        saveOrUpdate("Início e Fim", "Marcações visuais no código que mostram exatamente onde o programa começa e onde ele termina.", c3, "https://drive.google.com/file/d/1L3Bdlrgply4dNvLotBgAUImrnhjC267k/view?usp=drive_link");
        saveOrUpdate("Programa", "Conjunto de instruções salvas e organizadas para fazer o computador realizar uma tarefa.", c3, "https://www.youtube.com/watch?v=fMvMHh0bTOE");

        // 4. Variáveis e Tipos de Dados
        saveOrUpdate("Variável", "Uma 'caixa' na memória do computador para guardar uma informação que pode mudar durante o programa.", c4, "https://www.youtube.com/watch?v=iiTFMSFZSP4");
        saveOrUpdate("Atribuição", "Ação de colocar ou guardar um valor dentro de uma variável (usando o sinal de igual).", c4, "https://www.youtube.com/watch?v=AHIEkV00zmY");
        saveOrUpdate("Caractere (char)", "Uma única letra, número ou símbolo escrito dentro do código.", c4, "https://www.youtube.com/watch?v=ve2bz5b_HZw");
        saveOrUpdate("Dados", "Informações brutas (como textos, números ou imagens) que entram no programa para serem usadas.", c4, "https://www.youtube.com/watch?v=qLCs4pSuq6M");

        // 5. Operadores e Expressões
        saveOrUpdate("Multiplicação", "Operação matemática de multiplicar valores dentro do código (representada pelo símbolo *).", c5, "https://www.youtube.com/watch?v=QGA1OJsD53s");
        saveOrUpdate("Divisão", "Operação matemática de dividir valores dentro do código (representada pelo símbolo /).", c5, "https://www.youtube.com/watch?v=qpCvdMd9ZyU");

        // 6. Estruturas de Controle
        saveOrUpdate("Estrutura", "Como organizamos o código e as informações para o programa funcionar direito.", c6, "https://www.youtube.com/watch?v=Jps368YhFnY");
        saveOrUpdate("Para (for)", "Repete um bloco de comandos por uma quantidade de vezes que já sabemos antes.", c6, "https://www.youtube.com/watch?v=6INXGmjqVEw");
        saveOrUpdate("Repita (repeat)", "Repete comandos até que uma regra de parada (condição) aconteça.", c6, "https://www.youtube.com/watch?v=JxrP_HKmJCo");
        saveOrUpdate("Sair", "Comando usado para fechar o programa ou interromper uma repetição.", c6, "https://www.youtube.com/watch?v=8-rDLV_TMhY");

        // 7. Estruturas de Dados Básicas
        saveOrUpdate("Vetor (array)", "Uma lista de caixas organizadas em sequência na memória para guardar vários valores do mesmo tipo.", c7, "https://www.youtube.com/watch?v=OvqDSeuCVAw");
        saveOrUpdate("Matriz", "Uma tabela organizada em linhas e colunas para guardar várias informações juntas.", c7, "https://www.youtube.com/watch?v=1VHsj9TUpgs");

        // 9. Entrada e Saída de Dados
        saveOrUpdate("Leitura de dados (Leia)", "Quando o programa recebe e lê uma informação digitada pelo usuário no teclado.", c9, "https://www.youtube.com/watch?v=hcGLnB5nodQ");
        saveOrUpdate("Escrita de dados (Escrever)", "Comando para mostrar uma mensagem de texto ou resultado na tela.", c9, "https://www.youtube.com/watch?v=KqBpH4loruk");
        saveOrUpdate("Imprimir", "Mostrar um texto na tela do computador ou enviá-lo para uma folha de papel física.", c9, "https://www.youtube.com/watch?v=mUnj8QhV3Ag");

        // 10. Conceitos Computacionais Complementares
        saveOrUpdate("Hardware", "Toda a parte física do computador que podemos tocar (como telas, placas, chips e fios).", c10, "https://drive.google.com/file/d/1_3NcbF6BUKTuRapGwLYbYju8NTk2zNd-/view?usp=drive_link");
        saveOrUpdate("Software", "Os programas, aplicativos e sistemas virtuais que fazem o computador funcionar.", c10, "https://drive.google.com/file/d/1L8l1z7RdTVf7y6L3r5ueygqdz-Cp7ton/view?usp=drive_link");

        System.out.println(">>> SignSeeder: Glossário atualizado com termos e vídeos do Google Drive.");
    }
}