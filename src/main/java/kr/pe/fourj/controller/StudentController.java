package kr.pe.fourj.controller;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.pe.fourj.domain.Catalog;
import kr.pe.fourj.domain.Course;
import kr.pe.fourj.domain.Student;
import kr.pe.fourj.dto.ResponseDTO;
import kr.pe.fourj.dto.StudentDTO;
import kr.pe.fourj.exception.Exception.ArgumentNullException;
import kr.pe.fourj.exception.Exception.NotFoundException;
import kr.pe.fourj.repository.StudentRepository;
import kr.pe.fourj.service.CatalogService;
import kr.pe.fourj.service.CourseService;
import kr.pe.fourj.service.StudentService;

@RestController
public class StudentController {

	@Autowired
	private StudentService studentService;
	@Autowired
	private CatalogService catalogService;
	@Autowired
	private CourseService courseService;
	@Autowired
	StudentRepository studentRepository;

	//학생 저장
	@PostMapping("/student")
	public ResponseDTO.Create saveStudent(@RequestBody StudentDTO.Create dto) throws NotFoundException {
		System.out.println("-- 학생 저장시도 --");

		boolean result = false;
		Long saveId = null;
		
		LocalDate now = LocalDate.now();
		LocalDate birth = dto.getBirth();
		
		Period period = Period.between(birth, now);
		int age = period.getYears() + 1;


		if (studentService.findStudentById(dto.getId()) == null) {
			try {
				saveId = studentService.saveStudent(new Student(dto.getId(), dto.getPw(), dto.getName(), dto.getBirth(),
						age, dto.getNickName(), dto.getGender(), dto.getAddress(), dto.getPhone()));
				result = true;					
			} catch (ArgumentNullException e) {
				e.printStackTrace();
			}
		}else {
			System.out.println("이미 존재하는 회원id입니다.");
		}

		return new ResponseDTO.Create(saveId, result);
	}
	
	//학생 로그인
	@RequestMapping(value="/student/login", method=RequestMethod.POST) 
	public ResponseDTO.Login login(HttpServletRequest request, @RequestBody StudentDTO.Login dto) {

		boolean result = false;
		Student student = studentService.findStudentById(dto.getId());
		if(student != null) {
			if(student.getPw().equals(dto.getPw()) && request.getSession().getAttribute("student") == null) {
				request.getSession().setAttribute("student", student);
				
				Object object = request.getSession().getAttribute("student");
				Student entity = (Student)object;
				System.out.println(entity.getId());
				System.out.println(entity.getPw());
				
				result = true;
				System.out.println(entity.getId() + " " + entity.getPw() + " " + "로그인 성공!");
			}else {
				System.out.println("로그인 실패! : 패스워드를 다시 확인해주세요. 중복 로그인은 불가합니다.");
			}
		}else {
			System.out.println("로그인 실패! : 등록되지 않은 회원입니다.");
		}

		return new ResponseDTO.Login(result);
	}
	
	//학생 로그아웃
	@RequestMapping("/student/logout")
	public ResponseDTO.Logout logout(HttpServletRequest request) {
		
		boolean result = false;
		if(request.getSession().getAttribute("student") != null) {
			
			Object object = request.getSession().getAttribute("student");
			Student entity = (Student)object;
			System.out.println(entity.getId());
			System.out.println(entity.getPw());
			
			System.out.println(entity.getId() + " " + entity.getPw() + " " + "로그아웃 성공!");
			request.getSession().removeAttribute("student");
			result = true;
		}else {
			System.out.println("로그아웃 실패! : 로그인이 되어있지 않은 상태에서는 로그아웃이 불가합니다.");
		}
				
		return new ResponseDTO.Logout(result);
	}
	

	//학생 수정
	@PutMapping("/student")
	public ResponseDTO.Update updateStudent(HttpServletRequest request, @RequestBody StudentDTO.Update dto) {
		System.out.println("-- 학생 수정 시도 --");

		boolean result = false;
		if(request.getSession().getAttribute("student") != null) {
			Object object = request.getSession().getAttribute("student");
			Student entity = (Student)object;

			try {
				studentService.updateStudent(entity.getIdx(), dto);
				result = true;
			} catch (NotFoundException e) {
				e.printStackTrace();
			}
		}else {
			System.out.println("로그인 정보가 없습니다. 로그인이 필요한 기능입니다.");
		}
		
		return new ResponseDTO.Update(result);
	}

	//학생 삭제
	@DeleteMapping("/student")
	public ResponseDTO.Delete deleteStudent(HttpServletRequest request) {
		System.out.println("-- 학생 삭제 시도 --");

		boolean result = false;
		if(request.getSession().getAttribute("student") != null) {
			Object object = request.getSession().getAttribute("student");
			Student entity = (Student)object;

			try {
				studentService.deleteStudent(entity.getIdx());
				request.getSession().removeAttribute("student"); //학생 삭제 후 바로 로그아웃 되도록
				result = true;
			} catch (NotFoundException e) {
				e.printStackTrace();
			}		
		}else {
			System.out.println("로그인 정보가 없습니다. 로그인이 필요한 기능입니다.");
		}

		return new ResponseDTO.Delete(result);
	}

	//학생 단일 검색
	@GetMapping("/student")
	public ResponseDTO.StudentResponse findOne(HttpServletRequest request) {
		System.out.println("-- 학생 단일 검색 시도 --");
		boolean result = false;
		Student student = null;
		if(request.getSession().getAttribute("student") != null) {
			Object object = request.getSession().getAttribute("student");
			Student entity = (Student)object;

			try {
				student = studentService.findOne(entity.getIdx());
				result = true;
			} catch (NotFoundException e) {
				e.printStackTrace();
			}
		}else {
			System.out.println("로그인 정보가 없습니다. 로그인이 필요한 기능입니다.");
		}
		return new ResponseDTO.StudentResponse(result, student);
	}

	//???전체 학생 리스트를 검색하는 게 쓰일까요???
	//학생 리스트 전체 검색
	@GetMapping("/studentall")
	public ResponseDTO.StudentListResponse findAll() {
		System.out.println("-- 학생 리스트 전체 검색 시도 --");

		List<Student> studentList = studentService.findAll();

		return new ResponseDTO.StudentListResponse(true, studentList);
	}

	//특정 강의를 수강하는 학생 리스트 검색
	@GetMapping("/student/courseIdx")
	public ResponseDTO.StudentListResponse findAllBycourseIdx(@RequestBody StudentDTO.Get dto) {
		System.out.println("-- 특정 강의를 수강하는 학생 리스트 검색 시도 --");

		boolean result = false;
		List<Student> studentList = new ArrayList<Student>();
		try {
			Course course = courseService.findOne(dto.getCourseIdx());
			List<Catalog> catalogList = catalogService.findAllByCourseIdx(course);
			catalogList.forEach(e -> studentList.add(e.getStudentIdx()));
			result = true;
		} catch (NotFoundException e1) {
			e1.printStackTrace();
		}

		return new ResponseDTO.StudentListResponse(result, studentList);
	}

}
