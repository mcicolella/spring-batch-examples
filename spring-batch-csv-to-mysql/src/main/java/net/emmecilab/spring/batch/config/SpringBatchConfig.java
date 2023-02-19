package net.emmecilab.spring.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import net.emmecilab.spring.batch.job.PlayerItemProcessor;
import net.emmecilab.spring.batch.model.Player;
import net.emmecilab.spring.batch.repository.PlayerRepository;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

	private JobBuilderFactory jobBuilderFactory;
	private StepBuilderFactory stepBuilderFactory;
	private PlayerRepository playerRepository;

	public SpringBatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
			PlayerRepository playerRepository) {
		this.jobBuilderFactory = jobBuilderFactory;
		this.stepBuilderFactory = stepBuilderFactory;
		this.playerRepository = playerRepository;
	}

	@Bean
	public FlatFileItemReader<Player> reader() {
		FlatFileItemReader<Player> itemReader = new FlatFileItemReader<>();
		itemReader.setResource(new FileSystemResource("src/main/resources/data/players.csv"));
		itemReader.setName("csvReader");
		itemReader.setLinesToSkip(1);
		itemReader.setLineMapper(lineMapper());
		return itemReader;
	}

	private LineMapper<Player> lineMapper() {
		DefaultLineMapper<Player> lineMapper = new DefaultLineMapper<>();

		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter(",");
		lineTokenizer.setStrict(false);
		lineTokenizer.setNames("id", "firstName", "lastName", "position", "team");

		BeanWrapperFieldSetMapper<Player> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(Player.class);

		lineMapper.setLineTokenizer(lineTokenizer);
		lineMapper.setFieldSetMapper(fieldSetMapper);
		return lineMapper;

	}

	@Bean
	public PlayerItemProcessor processor() {
		return new PlayerItemProcessor();
	}

	@Bean
	public RepositoryItemWriter<Player> writer() {
		RepositoryItemWriter<Player> writer = new RepositoryItemWriter<>();
		writer.setRepository(playerRepository);
		writer.setMethodName("save");
		return writer;
	}

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("csv-step").<Player, Player>chunk(10)
				.reader(reader())
				.processor(processor())
				.writer(writer())
				//.taskExecutor(taskExecutor())
				.build();
	}

	@Bean
	public Job runJob() {
		return jobBuilderFactory
				.get("importPlayers")
				.flow(step1())
				.end()
				.build();

	}

	//@Bean
	//public TaskExecutor taskExecutor() {
	//	SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
	//	asyncTaskExecutor.setConcurrencyLimit(10);
	//	return asyncTaskExecutor;
	//}

}