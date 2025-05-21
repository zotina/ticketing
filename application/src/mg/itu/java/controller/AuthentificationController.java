package mg.itu.java.controller;
import framework.ModelView;
import framework.Session;
import framework.Annotation.Controller;
import framework.Annotation.Url;
import framework.Annotation.Param;
import framework.Annotation.Post;
import framework.Annotation.Valid;
import mg.itu.java.database.Connexion;
import framework.FileUpload;
import mg.itu.java.model.Utilisateur;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;


@Controller
public class AuthentificationController {

    private final String ROLE = "admin";
    
    @Url("/")
    public ModelView index() {
        ModelView modelview = new ModelView("./page/user/login_utilisateur.jsp");
        return modelview;
    }

    @Url("/admin")
    public ModelView admin() {
        ModelView modelview = new ModelView("./page/admin/login_admin.jsp");
        return modelview;
    }

    @Url("/sign_up")
    public ModelView sign_up() {
        ModelView modelview = new ModelView("./page/user/sign_up_utilisateur.jsp");
        return modelview;
    }
    @Url("/logout")
    public ModelView login_out(@Param("session") Session session) {
        ModelView modelview = new ModelView("./page/user/login_utilisateur.jsp");
        session.delete("user");
        session.delete("auth");
        session.delete("panier");
        return modelview;
    }

    @Url("/process_login_user")
    @Post
    public ModelView login_user(@Param("utilisateur") Utilisateur u ,@Param("session") Session session) {
        ModelView modelview = new ModelView();
        Connection conn = new Connexion().connect_to_postgres();
        try {
            Utilisateur user = Utilisateur.authentification(u.getEmail(), u.getMdp(), conn,null);
            if(user == null ){
                modelview.setUrl("./page/user/login_utilisateur.jsp");
                modelview.add("error","invalid email or password");
            }else{
                modelview.setUrl("./page/acceuil.jsp");
                session.add("user",user);
                session.add("auth","user");
            }
        } catch (Exception e) {
            e.printStackTrace();
            modelview.setUrl("./page/user/login_utilisateur.jsp");
                modelview.add("error","erreur happend");
        }finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

        return modelview;
    }

    @Url("/process_login_admin")
    @Post
    public ModelView login_admin(@Param("utilisateur") Utilisateur u ,@Param("session") Session session) {
        ModelView modelview = new ModelView();
        Connection conn = new Connexion().connect_to_postgres();

        try {
            Utilisateur user = Utilisateur.authentification(u.getEmail(), u.getMdp(), conn,ROLE);
            if(user == null ){
                modelview.setUrl("./page/admin/login_admin.jsp");
                modelview.add("error","invalid email or password");
            }else{
                modelview.setUrl("./page/acceuil.jsp");
                session.add("user",user);
                session.add("auth","admin");
            }
        } catch (Exception e) {
            e.printStackTrace();
            modelview.setUrl("./page/admin/login_admin.jsp");
                modelview.add("error","erreur happend");
        }finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

        return modelview;
    }

    @Url("/process_sign_up")
    @Post
    public ModelView process_sign_up(@Valid @Param("utilisateur") Utilisateur u ,@Param("passport")FileUpload passeport) {
        ModelView modelview = new ModelView();
        Connection conn = new Connexion().connect_to_postgres();
        try {
            System.out.println(passeport.getPath());
            u.setPassport(passeport.getPath());
            u.setMdp(BCrypt.hashpw(u.getMdp(), BCrypt.gensalt()));  
            u.insert(conn);
            modelview.setUrl("./page/user/login_utilisateur.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            modelview.setUrl("./page/user/sign_up_utilisateur.jsp");
            modelview.add("error", "Erreur lors de l'inscription");
        } finally {
            if (conn != null) try { conn.close(); } catch (Exception e) {}
        }
        return modelview;
    }
    

}