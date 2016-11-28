package audio.core;

import java.io.File;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.musicg.wave.Wave;

public class Core extends ApplicationAdapter {

	private OrthographicCamera camera;
	private Database database;
	private RecordAudio rec;
	private GraphicRender render;

	private final String SPECTROGRAM_LOCATION = "D:/Audio Recoginition/Gradle/android/assets/spectrogram.jpg";
	private Texture tex;
	private SpriteBatch batch;
	private ShapeRenderer shape;
	
	@Override
	public void create() {
		camera = new OrthographicCamera(1, Gdx.graphics.getHeight()/Gdx.graphics.getWidth());
		camera.update();
		database = new Database();
		rec = new RecordAudio();
		render = new GraphicRender();
		batch = new SpriteBatch();
		shape = new ShapeRenderer();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// TEST
		if (Gdx.input.isKeyJustPressed(Keys.T)) {
			Wave w = new Wave("D:/Audio Recoginition/Gradle/android/assets/files/BattleRoyal-DopeDOD.wav");
			database.search(w);
			render.renderSpectrogram(w.getSpectrogram(), SPECTROGRAM_LOCATION);
		}
		
		// TEST
		if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
			Wave w = rec.record();
			database.search(w);
			render.renderSpectrogram(w.getSpectrogram(), SPECTROGRAM_LOCATION);
			tex = new Texture(new FileHandle(new File(SPECTROGRAM_LOCATION)));
			
		}
		if (Gdx.input.isKeyJustPressed(Keys.S)) {
			database.save();
		}

		if (Gdx.input.isKeyJustPressed(Keys.L)) {
			database.load();
		}

		if (Gdx.input.isKeyJustPressed(Keys.B)) {
			database.rebuildInternal();
		}
		
		if(tex != null) {
			
			
			batch.begin();
			batch.draw(tex, 0, 0);
			batch.end();
			
			
			shape.begin(ShapeType.Filled);
			shape.setColor(Color.BLACK);
			shape.rect(0, 0, 150, Analyzer.LOWER_LIMIT);
			shape.end();
			
			shape.begin(ShapeType.Filled);
			shape.setColor(Color.GREEN);
			shape.rect(0, Analyzer.LOWER_LIMIT, 150, Analyzer.RANGE[0]);
			shape.end();
			
			shape.begin(ShapeType.Filled);
			shape.setColor(Color.YELLOW);
			shape.rect(0, Analyzer.RANGE[0], 150, Analyzer.RANGE[1]);
			shape.end();
			
			shape.begin(ShapeType.Filled);
			shape.setColor(Color.ORANGE);
			shape.rect(0, Analyzer.RANGE[1], 150, Analyzer.RANGE[2]);
			shape.end();
			
			shape.begin(ShapeType.Filled);
			shape.setColor(Color.RED);
			shape.rect(0, Analyzer.RANGE[2], 150, Analyzer.RANGE[3]);
			shape.end();
		
		}
		
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width;
		camera.viewportHeight = height;
		camera.update();
	}

	@Override
	public void dispose() {
		batch.dispose();
	}
}
