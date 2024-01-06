package gm.estudiantes;

import gm.estudiantes.modelo.Estudiante;
import gm.estudiantes.servicio.EstudianteServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
@ComponentScan("gm.estudiantes")
public class EstudiantesApplication implements CommandLineRunner {

	@Autowired
	private EstudianteServicio estudianteServicio;

	private static final Logger logger =
			LoggerFactory.getLogger(EstudiantesApplication.class);
	String nl = System.lineSeparator();
	public static void main(String[] args) {

		logger.info("Iniciando la app...");
		//levantar la fabrica de spring
		SpringApplication.run(EstudiantesApplication.class, args);
		logger.info("app finalizada...");
	}
	@Override
	public void run(String... args) throws Exception {
		logger.info(nl + "Ejecutando metodo run de spring..." + nl);
		var salir = false;
		var consola = new Scanner(System.in);
		while (!salir){
			mostrarMenu();
			salir = ejecutarOpciones(consola);
			logger.info(nl);
		}//fin while
	}
	private void mostrarMenu(){
		logger.info(nl);
		logger.info("""
    **** Sistema de Estudiantes ****
    1. Listar Estudiantes
    2. Buscar Estudiante
    3. Agregar Estudiante
    4. Modificar Estudiante
    5. Eliminar Estudiante
    6. Salir
    Elige una opcion:""");
	}
	private boolean ejecutarOpciones(Scanner consola){
		var opcion = Integer.parseInt(consola.nextLine());
		var salir = false;
		switch (opcion){
			case 1:// Listar estudiantes
				logger.info(nl + "Listado de estudiantes..." + nl);
				List<Estudiante> estudiantes = estudianteServicio.listarEstudiantes();
				estudiantes.forEach((estudiante -> logger.info(estudiante.toString()+ nl)));
				break;
			case 2:// Buscar Estudiante
				logger.info("Introduce el id del estudiante a buscar: ");
				var idEstudiante = Integer.parseInt(consola.nextLine());
				Estudiante estudiante = estudianteServicio.buscarEstudiantePorID(idEstudiante);
				if (estudiante != null){
					logger.info("Estudiante encontrado: " + estudiante + nl);
				} else {
					logger.info("Estudiante no encontrado con id: " + idEstudiante + nl);
				}
				break;
			case 3:// Agregar Estudiante
				logger.info("Agregar Estudiante: " + nl);
				logger.info("Nombre: ");
				var nombre = consola.nextLine();
				logger.info("Apellido: ");
				var apellido = consola.nextLine();
				logger.info("telefono: ");
				var telefono = consola.nextLine();
				logger.info("Email: ");
				var email = consola.nextLine();
				//crear objeto estudiante sin el id
				var estudianteAAgregar = new Estudiante();
				estudianteAAgregar.setNombre(nombre);
				estudianteAAgregar.setApellido(apellido);
				estudianteAAgregar.setTelefono(telefono);
				estudianteAAgregar.setEmail(email);
				estudianteServicio.guardarEstudiante(estudianteAAgregar);
				logger.info("Estudiante agregado: " + estudianteAAgregar +  nl);
				break;
			case 4:// Modificar Estudiante
				logger.info("Modificar estudiante: " + nl);
				logger.info("Id estudiante: ");
				var idEstudianteAModificar = Integer.parseInt(consola.nextLine());
				// buscamos el estudiante a modificar
				Estudiante estudiante1 = estudianteServicio.buscarEstudiantePorID(idEstudianteAModificar);
				if (estudiante1 != null){
					logger.info("Nombre: ");
					var nombreAModificar = consola.nextLine();
					logger.info("Apellido: ");
					var apellidoAModificar = consola.nextLine();
					logger.info("telefono: ");
					var telefonoAModificar = consola.nextLine();
					logger.info("Email: ");
					var emailAModificar = consola.nextLine();
					estudiante1.setNombre(nombreAModificar);
					estudiante1.setApellido(apellidoAModificar);
					estudiante1.setTelefono(telefonoAModificar);
					estudiante1.setEmail(emailAModificar);
					estudianteServicio.guardarEstudiante(estudiante1);
					logger.info("EStudiante modificado: " + estudiante1 + nl);
				} else{
					logger.info("Estudiante NO encontrado con id: " + idEstudianteAModificar + nl);
				}
				break;
			case 5:// Eliminar Estudiante
				logger.info("Eliminar estudiante: " + nl);
				logger.info("Id estudiante: ");
				var idEstudianteAEliminar = Integer.parseInt(consola.nextLine());
				//buscamos el id a eliminar
				var estudianteAEliminar = estudianteServicio.buscarEstudiantePorID(idEstudianteAEliminar);
				if (estudianteAEliminar != null){
					estudianteServicio.eliminarEstudiante(estudianteAEliminar);
					logger.info("Estudiante eliminado: " + estudianteAEliminar + nl);
				}else{
					logger.info("Estudiante NO encontrado con id: " + idEstudianteAEliminar);
				}
				break;
			case 6:// SALIR
				logger.info("Hasta pronto..."+nl + nl);
				salir = true;
				break;
			default:
				logger.info("Opcion NO reconocida..." + opcion + nl);
				break;
		}//fin switch
		return salir;
	}
}
