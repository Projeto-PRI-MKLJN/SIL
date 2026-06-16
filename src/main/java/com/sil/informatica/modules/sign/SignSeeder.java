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

    @Override
    public void run(String... args) {
        if (signRepository.count() == 0) {
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

            signRepository.saveAll(Arrays.asList(
                // 1. Conceitos Fundamentais
                new Sign("Lógica", "Organizar os pensamentos de forma clara e correta para criar programas de computador.", c1, "https://www.youtube.com/watch?v=Rhi0q7Yd-uc"),
                new Sign("Algoritmo", "Passo a passo organizado para resolver um problema no computador.", c1, "https://www.youtube.com/watch?v=eaEuisD5QAA"),

                // 2. Representação de Algoritmos
                new Sign("Linguagem de programação", "Conjunto de palavras e regras que usamos para escrever um programa de computador.", c2, "https://www.youtube.com/watch?v=649xQ-IfRH4"),

                // 3. Estrutura Básica de um Programa
                new Sign("Entrada (input)", "Enviar informações ou dados para dentro de um programa de computador.", c3, "https://www.youtube.com/watch?v=xh08vIigql8"),
                new Sign("Processamento", "Quando o computador calcula, organiza ou transforma os dados recebidos.", c3, "https://www.youtube.com/watch?v=h2cZ5zb1JH0"),
                new Sign("Saída (output)", "As informações ou resultados que o computador mostra na tela.", c3, "https://www.youtube.com/watch?v=fCKbRcYggwQ"),
                new Sign("Programa", "Instruções escritas para fazer o computador realizar uma tarefa.", c3, "https://www.youtube.com/watch?v=fMvMHh0bTOE"),

                // 4. Variáveis e Tipos de Dados
                new Sign("Variável", "Um espaço na memória do computador para guardar um valor que pode mudar.", c4, "https://www.youtube.com/watch?v=iiTFMSFZSP4"),
                new Sign("Atribuição", "Colocar ou guardar um valor dentro de uma variável.", c4, "https://www.youtube.com/watch?v=AHIEkV00zmY"),
                new Sign("Caractere (char)", "Uma única letra, número ou símbolo no código.", c4, "https://www.youtube.com/watch?v=ve2bz5b_HZw"),
                new Sign("Dados", "Valores ou informações que entram no computador para serem processados.", c4, "https://www.youtube.com/watch?v=qLCs4pSuq6M"),

                // 5. Operadores e Expressões
                new Sign("Multiplicação", "Operação matemática de multiplicar valores.", c5, "https://www.youtube.com/watch?v=QGA1OJsD53s"),
                new Sign("Divisão", "Operador aritmético de dividir valores.", c5, "https://www.youtube.com/watch?v=qpCvdMd9ZyU"),

                // 6. Estruturas de Controle
                new Sign("Estrutura", "Como organizamos o código e as informações no programa.", c6, "https://www.youtube.com/watch?v=Jps368YhFnY"),
                new Sign("Para (for)", "Repete um bloco de comandos por um número de vezes que já sabemos.", c6, "https://www.youtube.com/watch?v=6INXGmjqVEw"),
                new Sign("Repita (repeat)", "Repete comandos até que uma condição seja verdadeira.", c6, "https://www.youtube.com/watch?v=JxrP_HKmJCo"),
                new Sign("Sair", "Comando para fechar ou encerrar um programa ou repetição.", c6, "https://www.youtube.com/watch?v=8-rDLV_TMhY"),

                // 7. Estruturas de Dados Básicas
                new Sign("Vetor (array)", "Uma lista ordenada de informações do mesmo tipo.", c7, "https://www.youtube.com/watch?v=OvqDSeuCVAw"),
                new Sign("Matriz", "Uma tabela com linhas e colunas para guardar informações.", c7, "https://www.youtube.com/watch?v=1VHsj9TUpgs"),

                // 9. Entrada e Saída de Dados
                new Sign("Leitura de dados (Leia)", "Quando o programa lê ou recebe o que o usuário digitou.", c9, "https://www.youtube.com/watch?v=hcGLnB5nodQ"),
                new Sign("Escrita de dados (Escrever)", "Mostrar uma mensagem ou resultado na tela do computador.", c9, "https://www.youtube.com/watch?v=KqBpH4loruk"),
                new Sign("Imprimir", "Mostrar informações na tela ou enviar para a impressora.", c9, "https://www.youtube.com/watch?v=mUnj8QhV3Ag"),

                // 10. Conceitos Computacionais Complementares
                new Sign("Hardware", "A parte física do computador que podemos tocar (peças, placas e fios).", c10, "https://www.youtube.com/watch?v=ONpb-9r0SsY"),
                new Sign("Software", "Os programas e sistemas que fazem o computador funcionar.", c10, "https://www.youtube.com/watch?v=PkmmkGSWfHY")
            ));
            System.out.println(">>> SignSeeder: Glossário populado com termos do canal Informática em LIBRAS.");
        }
    }
}