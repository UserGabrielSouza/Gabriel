/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logs;

/**
 *
 * @author Lenovo
 */
import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.processador.Processador;
import com.github.britooo.looca.api.util.Conversor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Logs {
    private static final String DIRETORIO_LOGS = Paths.get(System.getProperty("user.home"), "PastaSam").toString();
    private static final String PREFIXO_ARQUIVO_LOG = "log_";
    private static final long INTERVALO_LOG = TimeUnit.MINUTES.toMillis(1);
    private static final long INTERVALO_VERIFICACAO_LOG = TimeUnit.SECONDS.toMillis(20);

    private static List<String> logs = new ArrayList<>();
    private static Date horaInicio;
    private static Date horaUltimoArquivoLog;

    private static String gerarMensagemLog(Date timestamp, Memoria memoria,Conversor conversor) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
        long memoriaUtilizada = memoria.getEmUso().longValue();
        String usoMemoriaFormatado = conversor.formatarBytes(memoriaUtilizada);

        return String.format("%s - Uso de memória: %s ", sdf.format(timestamp), usoMemoriaFormatado);
    }

    private static void criarArquivoLog(List<String> logs) {
        if (logs.isEmpty()) {
            return;
        }

        SimpleDateFormat sdfNomeArquivoLog = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String nomeArquivoLog = "logs_" + sdfNomeArquivoLog.format(horaUltimoArquivoLog) + ".txt";

        try {
            FileWriter writer = new FileWriter(Paths.get(DIRETORIO_LOGS, nomeArquivoLog).toString(), true);

            for (String log : logs) {
                writer.write(log);
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void startLogging() throws InterruptedException {
        File diretorio = new File(DIRETORIO_LOGS);
        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }

        Looca looca = new Looca();
        Memoria memoria = looca.getMemoria();
        Processador processador = looca.getProcessador();
        Conversor conversor = new Conversor();

        horaInicio = new Date();
        horaUltimoArquivoLog = horaInicio;

        while (true) {
            Date agora = new Date();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
            SimpleDateFormat sdfNomeArquivoLog = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");

            long tempoDecorrido = agora.getTime() - horaUltimoArquivoLog.getTime();

            if (tempoDecorrido >= INTERVALO_LOG) {
                criarArquivoLog(logs);
                logs.clear();
                horaUltimoArquivoLog = agora;
            }

            String mensagemLog = gerarMensagemLog(agora, memoria,conversor);
            System.out.print(mensagemLog);

            logs.add(mensagemLog);

            TimeUnit.MILLISECONDS.sleep(INTERVALO_VERIFICACAO_LOG);
        }
    }
}
