package mg.itu.java.controller;

import java.sql.Connection;
import framework.ModelView;
import framework.Annotation.Url;
import framework.Annotation.Param;
import framework.Annotation.Controller;
import mg.itu.java.database.Connexion;
import mg.itu.java.model.Annulation_reservation;
import mg.itu.java.model.Critere_reservation;
import mg.itu.java.model.Promotion;
import mg.itu.java.model.Promotion_siege;
import mg.itu.java.model.Type_siege;
import mg.itu.java.model.Vol;
import framework.Annotation.Auth;

import framework.Annotation.Post;

@Controller
@Auth("admin")
public class DetailVolController {

    @Url("/detail_form")
    public ModelView form() {
        ModelView modelview = new ModelView("./page/detail_vol/formulaireDetail.jsp");
        Connection conn = new Connexion().connect_to_postgres();
        try {
            modelview.add("volList", Vol.getAll(conn));
            modelview.add("typeSiegeList", Type_siege.getAll(conn));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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

    @Url("/promotion_insert")
    @Post
    public ModelView insert(@Param("promotion") Promotion promotion, @Param("promotion_siege") Promotion_siege[] promotion_sieges) {
        ModelView modelview = new ModelView();
        Connection conn = new Connexion().connect_to_postgres();
        try {
            conn.setAutoCommit(false);

            promotion.insert(conn);

            if (promotion_sieges != null) { 
                for (Promotion_siege siege : promotion_sieges) {
                    if (siege != null) {  
                        siege.setPromotion(promotion);
                        siege.insert(conn);
                    }
                }
            }

            conn.commit();

            modelview.add("volList", Vol.getAll(conn));
            modelview.add("typeSiegeList", Type_siege.getAll(conn));
            modelview.setUrl("./page/detail_vol/formulaireDetail.jsp");
            modelview.add("message","Promotion valider");
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            modelview.setUrl("./page/detail_vol/formulaireDetail.jsp");
            modelview.add("message", "Une erreur est survenue lors de l'insertion");
        } finally {
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

    @Url("/critere_reservation_insert")
    @Post
    public ModelView insert_critere(@Param("critere_reservation") Critere_reservation critere_reservation) {
        ModelView modelview = new ModelView();
        Connection conn = new Connexion().connect_to_postgres();
        try {

            critere_reservation.insert(conn);

            modelview.add("volList", Vol.getAll(conn));
            modelview.add("typeSiegeList", Type_siege.getAll(conn));
            modelview.setUrl("./page/detail_vol/formulaireDetail.jsp");
            modelview.add("message","critere de reservation mise a jour");
        } catch (Exception e) {
            e.printStackTrace();
            modelview.setUrl("./page/detail_vol/formulaireDetail.jsp");
            modelview.add("message", "Une erreur est survenue lors de l'insertion");
        } finally {
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

    @Url("/annulation_reservation_insert")
    @Post
    public ModelView insert_annulation(@Param("annulation_reservation") Annulation_reservation annulation_reservation) {
        ModelView modelview = new ModelView();
        Connection conn = new Connexion().connect_to_postgres();
        try {

            annulation_reservation.insert(conn);

            modelview.add("volList", Vol.getAll(conn));
            modelview.add("typeSiegeList", Type_siege.getAll(conn));
            modelview.setUrl("./page/detail_vol/formulaireDetail.jsp");
            modelview.add("message","annulation mise a jour");
        } catch (Exception e) {
            e.printStackTrace();
            modelview.setUrl("./page/detail_vol/formulaireDetail.jsp");
            modelview.add("message", "Une erreur est survenue lors de l'insertion");
        } finally {
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
}
